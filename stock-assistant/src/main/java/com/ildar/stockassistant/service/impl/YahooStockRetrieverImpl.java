package com.ildar.stockassistant.service.impl;

import com.ildar.stockassistant.dto.StockDTO;
import com.ildar.stockassistant.dto.StockDividendDTO;
import com.ildar.stockassistant.service.AsyncStockInfoRetriever;
import com.ildar.stockassistant.service.ReactiveStockInfoRetriever;
import com.ildar.stockassistant.service.StockInfoRetriever;
import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Optional.ofNullable;

@Getter
public class YahooStockRetrieverImpl implements StockInfoRetriever, AsyncStockInfoRetriever, ReactiveStockInfoRetriever {

    private final String websiteURL;
    private final WebClient webClient;

    private static final int MAX_IN_MEMORY_SIZE = 16 * 1024 * 1024;

    private static final String NOT_FOUND_STRING = "Symbols similar to '%s'";
    private static final String TRAILING_PE = "Trailing P/E";
    private static final String FORWARD_PE = "Forward P/E";
    private static final String PEG_RATIO = "PEG Ratio (5 yr expected)";
    private static final String BETA = "Beta (5Y Monthly)";
    private static final String MARKET_CAP = "Market Cap (intraday)";
    private static final String PRICE_TO_SALES = "Price/Sales";
    private static final String YEAR_PRICE_CHANGE = "52-Week Change";
    private static final String YEAR_HIGH = "52 Week High";
    private static final String YEAR_LOW = "52 Week Low";
    private static final String FORWARD_DIVIDEND_YIELD = "Forward Annual Dividend Yield";
    private static final String TRAILING_DIVIDEND_YIELD = "Trailing Annual Dividend Yield";
    private static final String PAYOUT_RATIO = "Payout Ratio";
    private static final String DIVIDEND_DATE = "Dividend Date";
    private static final String EX_DIVIDEND_DATE = "Ex-Dividend Date";

    private static final BigDecimal THOUSAND = BigDecimal.valueOf(1000L);
    private static final String NA = "N/A";
    private static final DateTimeFormatter DATE_PATTERN = ofPattern("MMM dd, yyyy");

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
        return ofNullable(findStockInfoAsync(ticker).join());
    }

    @Override
    public CompletableFuture<StockDTO> findStockInfoAsync(String ticker) {
        return findStockInfoMono(ticker).toFuture();
    }

    @Override
    public Mono<StockDTO> findStockInfoMono(String ticker) {
        return webClient.get().uri("", ticker)
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(statsHTML -> statsHTMLToStockDTO(ticker, statsHTML));
    }

    private Mono<StockDTO> statsHTMLToStockDTO(String ticker, String statsHTML) {
        if (statsHTML.contains(String.format(NOT_FOUND_STRING, ticker))) {
            return Mono.empty();
        }

        Document doc = Jsoup.parse(statsHTML);

        BigDecimal trailingPe = getTrailingPe(doc);
        BigDecimal forwardPe = getForwardPe(doc);
        BigDecimal peg = getPEG(doc);
        BigDecimal beta = getBeta(doc);
        BigDecimal currentPrice = getCurrentPrice(doc, ticker);
        BigDecimal marketCap = getMarketCap(doc);
        String currency = getCurrency(doc);
        BigDecimal priceSales = getPriceSales(doc);
        BigDecimal oneYearChange = get52WeekChange(doc);
        BigDecimal oneYearHigh = get52WeekHigh(doc);
        BigDecimal oneYearLow = get52WeekLow(doc);
        String description = getDescription(doc);

        return Mono.just(StockDTO.builder()
                .ticker(ticker)
                .description(description)
                .exchange("") //todo
                .currentPE(trailingPe)
                .forwardPE(forwardPe)
                .PEG(peg)
                .beta(beta)
                .currentPrice(currentPrice)
                .marketCap(marketCap)
                .currency(currency)
                .priceToSales(priceSales)
                .oneYearChangePercentage(oneYearChange)
                .oneYearHigh(oneYearHigh)
                .oneYearLow(oneYearLow)
                .dividend(dividend(doc))
                .build());
    }

    private StockDividendDTO dividend(Document doc) {
        BigDecimal forwardYield = valueFromNextTd(doc, FORWARD_DIVIDEND_YIELD);
        if (forwardYield == null) {
            return null;
        }

        BigDecimal trailingYield = valueFromNextTd(doc, TRAILING_DIVIDEND_YIELD);
        BigDecimal payoutRatio = valueFromNextTd(doc, PAYOUT_RATIO);
        LocalDate dividendDate = dateFromNextTd(doc, DIVIDEND_DATE);
        LocalDate exDividendDate = dateFromNextTd(doc, EX_DIVIDEND_DATE);

        return StockDividendDTO.builder()
                .trailingYield(trailingYield)
                .forwardYield(forwardYield)
                .payoutRatioPercentage(payoutRatio)
                .exDividendDate(exDividendDate)
                .paymentDate(dividendDate)
                .build();
    }

    private LocalDate dateFromNextTd(Document doc, String column) {
        String text = textFromNextTd(doc, column);
        return text == null ? null : LocalDate.parse(text, DATE_PATTERN);
    }

    private BigDecimal get52WeekLow(Document doc) {
        return valueFromNextTd(doc, YEAR_LOW);
    }

    private BigDecimal get52WeekHigh(Document doc) {
        return valueFromNextTd(doc, YEAR_HIGH);
    }

    private BigDecimal get52WeekChange(Document doc) {
        return valueFromNextTd(doc, YEAR_PRICE_CHANGE);
    }

    private BigDecimal getPriceSales(Document doc) {
        return valueFromNextTd(doc, PRICE_TO_SALES);
    }

    private String getCurrency(Document doc) {
        Element span = doc.select("span:contains(currency in)").first();
        return ofNullable(span)
                .map(Element::text)
                .map(text -> (text.split(" ")))
                .map(text -> text[text.length - 1])
                .orElse(null);
    }

    private BigDecimal getMarketCap(Document doc) {
        return valueFromNextTd(doc, MARKET_CAP);
    }

    private BigDecimal getCurrentPrice(Document doc, String ticker) {
        Element descriptionH1 = doc.select(
                "fin-streamer[data-field='regularMarketPrice'][data-symbol='" + ticker + "']").first();
        return ofNullable(descriptionH1)
                .map(Element::text)
                .map(text -> text.replace(",", ""))
                .map(BigDecimal::new)
                .orElse(null);
    }

    private String getDescription(Document doc) {
        Element descriptionH1 = doc.select("h1[class='D(ib) Fz(18px)']").first();
        return ofNullable(descriptionH1)
                .map(Element::text)
                .orElse(null);
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
        return toNumber(textFromNextTd(doc, column));
    }

    private String textFromNextTd(Document doc, String column) {
        Element span = doc.select("span:contains(" + column + ")").first();
        String result = ofNullable(span)
                .flatMap(element -> ofNullable(element.parent()))
                .flatMap(element -> ofNullable(element.nextElementSibling()))
                .map(Element::text)
                .orElse(null);
        return StringUtils.equals(result, NA) ? null : result;
    }

    private BigDecimal toNumber(String text) {
        if (text == null) {
            return null;
        }

        text = text.replace(",", "")
                .replace("B", "")
                .replace("%", "");
        if (text.endsWith("M")) {
            //convert to billions for market cap
            text = text.replace("M", "");
            BigDecimal value = new BigDecimal(text);
            return value.divide(THOUSAND);
        }

        return new BigDecimal(text);
    }
}
