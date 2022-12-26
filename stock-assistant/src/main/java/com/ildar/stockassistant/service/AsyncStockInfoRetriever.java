package com.ildar.stockassistant.service;

import com.ildar.stockassistant.dto.StockDTO;

import java.util.concurrent.CompletableFuture;

public interface AsyncStockInfoRetriever {

    CompletableFuture<StockDTO> findStockInfoAsync(String ticker);
}
