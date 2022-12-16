package com.ildar.stockassistant.service;

import com.ildar.stockassistant.dto.StockDTO;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface AsyncStockInfoRetriever {

    CompletableFuture<Optional<StockDTO>> findStockInfoAsync(String ticker);
}
