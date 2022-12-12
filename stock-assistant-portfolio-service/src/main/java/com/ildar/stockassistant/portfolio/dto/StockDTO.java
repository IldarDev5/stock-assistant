package com.ildar.stockassistant.portfolio.dto;

import com.ildar.stockassistant.portfolio.domain.Stock;

import javax.validation.constraints.NotBlank;

public record StockDTO(
        Long id,
        @NotBlank(message = "Ticker mustn't be empty.") String ticker,
        @NotBlank(message = "Stock description mustn't be empty.") String description,
        @NotBlank(message = "Exchange name mustn't be empty.") String exchange) {

    public static StockDTO of(Stock stock) {
        return new StockDTO(stock.getId(), stock.getTicker(), stock.getDescription(), stock.getExchange());
    }
}
