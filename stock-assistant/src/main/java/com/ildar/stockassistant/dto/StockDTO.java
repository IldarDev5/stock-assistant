package com.ildar.stockassistant.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record StockDTO(String ticker,
                       String description,
                       String exchange,
                       BigDecimal currentPE,
                       BigDecimal forwardPE,
                       BigDecimal PEG,
                       BigDecimal beta) { }
