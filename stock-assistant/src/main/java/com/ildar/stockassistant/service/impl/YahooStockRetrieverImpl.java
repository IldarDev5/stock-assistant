package com.ildar.stockassistant.service.impl;

import com.ildar.stockassistant.dto.StockDTO;
import com.ildar.stockassistant.service.AsyncStockInfoRetriever;
import com.ildar.stockassistant.service.StockInfoRetriever;
import lombok.Getter;
import lombok.NonNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static java.util.Optional.ofNullable;

@Getter
public class YahooStockRetrieverImpl implements StockInfoRetriever, AsyncStockInfoRetriever {

    private final String websiteURL;
    private final WebClient webClient;

    private static final int MAX_IN_MEMORY_SIZE = 16 * 1024 * 1024;

    private static final String NOT_FOUND_STRING = "Symbols similar to '%s'";
    private static final String TRAILING_PE = "Trailing P/E";
    private static final String FORWARD_PE = "Forward P/E";
    private static final String PEG_RATIO = "PEG Ratio (5 yr expected)";
    private static final String BETA = "Beta (5Y Monthly)";

    public YahooStockRetrieverImpl(String websiteURL) {
        this.websiteURL = websiteURL;
        final ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(MAX_IN_MEMORY_SIZE))
                .build();
        this.webClient = WebClient.builder()
                .baseUrl(websiteURL)
                .exchangeStrategies(strategies)
                .build();
    }

    public YahooStockRetrieverImpl() {
        this("https://finance.yahoo.com/quote/{ticker}/key-statistics");
    }

    @Override
    public Optional<StockDTO> findStockInfo(@NonNull String ticker) {
        return findStockInfoAsync(ticker).join();
    }

    @Override
    public CompletableFuture<Optional<StockDTO>> findStockInfoAsync(String ticker) {
        return webClient.get().uri("", ticker)
                .retrieve()
                .bodyToMono(String.class)
                .map(statsHTML -> statsHTMLToStockDTO(ticker, statsHTML))
                .toFuture();
    }

    private Optional<StockDTO> statsHTMLToStockDTO(String ticker, String statsHTML) {
        if (statsHTML.contains(String.format(NOT_FOUND_STRING, ticker))) {
            return Optional.empty();
        }

        Document doc = Jsoup.parse(statsHTML);

        BigDecimal trailingPe = getTrailingPe(doc);
        BigDecimal forwardPe = getForwardPe(doc);
        BigDecimal peg = getPEG(doc);
        BigDecimal beta = getBeta(doc);

        return Optional.of(StockDTO.builder()
                .ticker(ticker)
                .description("") //todo
                .exchange("") //todo
                .currentPE(trailingPe)
                .forwardPE(forwardPe)
                .PEG(peg)
                .beta(beta)
                .build());
    }

    private BigDecimal getBeta(Document doc) {
        return valueFromNextTd(doc, BETA);
    }

    private BigDecimal getPEG(Document doc) {
        return valueFromNextTd(doc, PEG_RATIO);
    }

    private BigDecimal getForwardPe(Document doc) {
        return valueFromNextTd(doc, FORWARD_PE);
    }

    private BigDecimal getTrailingPe(Document doc) {
        return valueFromNextTd(doc, TRAILING_PE);
    }

    private BigDecimal valueFromNextTd(Document doc, String column) {
        Element span = doc.select("span:contains(" + column + ")").first();
        return ofNullable(span)
                .flatMap(element -> ofNullable(element.parent()))
                .flatMap(element -> ofNullable(element.nextElementSibling()))
                .map(Element::text)
                .map(BigDecimal::new)
                .orElse(null);
    }
}
