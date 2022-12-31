package com.ildar.stockassistant.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record StockDividendDTO(BigDecimal trailingYield,
                               BigDecimal forwardYield,
                               BigDecimal payoutRatioPercentage,
                               LocalDate paymentDate,
                               LocalDate exDividendDate) {
}
