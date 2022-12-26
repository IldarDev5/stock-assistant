package com.ildar.stockassistant.service;

import com.ildar.stockassistant.domain.StockData;
import com.ildar.stockassistant.dto.StockDTO;
import com.ildar.stockassistant.dto.StockDataDTO;
import com.ildar.stockassistant.repository.StockDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class StockDataService {

    private final StockDataRepository stockDataRepository;
    private final ReactiveStockInfoRetriever stockInfoRetriever;

    public Mono<StockDataDTO> findByTicker(String ticker) {
        return stockInfoRetriever.findStockInfoMono(ticker)
                .doOnNext(stockDTO -> log.info("Successfully retrieved stock data with ticker {}.", ticker))
                .flatMap(this::saveOrUpdateInStorage)
                .map(StockDataDTO::from);
    }

    private Mono<StockData> saveOrUpdateInStorage(StockDTO stockDTO) {
        return stockDataRepository.save(StockData.from(stockDTO))
                .doOnNext(stockData -> log.info("Successfully updated stock {} in DB.", stockData.ticker()));
    }
}
