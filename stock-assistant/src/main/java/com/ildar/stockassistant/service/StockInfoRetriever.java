package com.ildar.stockassistant.service;

import com.ildar.stockassistant.dto.StockDTO;

import java.util.Optional;

public interface StockInfoRetriever {

    Optional<StockDTO> findStockInfo(String ticker);
}
