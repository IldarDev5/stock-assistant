package com.ildar.stockassistant.service;

import com.ildar.stockassistant.dto.StockDTO;
import reactor.core.publisher.Mono;

public interface ReactiveStockInfoRetriever {

    Mono<StockDTO> findStockInfoMono(String ticker);
}
