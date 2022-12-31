package com.ildar.stockassistant.dto;

import lombok.Builder;

import java.math.BigDecimal;

/**
 * @param ticker Quote ID on an exchange
 * @param description What company this ticker represents
 * @param exchange Exchange the ticker is traded on
 * @param currentPE Current price to earnings
 * @param forwardPE Forward price to earnings
 * @param PEG Price to earnings to growth ratio
 * @param beta
 * @param currentPrice Current stock price (as of close during the last trading session)
 * @param marketCap Market capitalization in billions
 * @param currency Currency of the ticker
 * @param priceToSales
 * @param oneYearChangePercentage 52-week price change in %
 * @param oneYearHigh 52-week high price
 * @param oneYearLow 52-week low price
 * @param dividend Data about dividend paid by the company. Null, if the company doesn't pay it.
 */
@Builder
public record StockDTO(String ticker,
                       String description,
                       String exchange,
                       BigDecimal currentPE,
                       BigDecimal forwardPE,
                       BigDecimal PEG,
                       BigDecimal beta,
                       BigDecimal currentPrice,
                       BigDecimal marketCap,
                       String currency,
                       BigDecimal priceToSales,
                       BigDecimal oneYearChangePercentage,
                       BigDecimal oneYearHigh,
                       BigDecimal oneYearLow,
                       StockDividendDTO dividend) { }
