package com.ildar.stockassistant.dto;

import com.ildar.stockassistant.domain.StockData;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record StockDataDTO(String ticker,
                           String description,
                           String exchange,
                           BigDecimal currentPE,
                           BigDecimal forwardPE,
                           BigDecimal PEG,
                           BigDecimal beta) {
    public static StockDataDTO from(StockData stockData) {
        return StockDataDTO.builder()
                .ticker(stockData.ticker())
                .exchange(stockData.exchange())
                .description(stockData.description())
                .currentPE(stockData.currentPE())
                .forwardPE(stockData.forwardPE())
                .beta(stockData.beta())
                .PEG(stockData.PEG())
                .build();
    }
}
