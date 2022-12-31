package com.ildar.stockassistant.service.impl;

import com.ildar.stockassistant.dto.StockDTO;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static java.math.BigDecimal.ZERO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class YahooStockRetrieverImplTest {

    private static final Logger log = LoggerFactory.getLogger(YahooStockRetrieverImplTest.class);

    private final YahooStockRetrieverImpl stockInfoRetriever = new YahooStockRetrieverImpl();

    @Test
    public void testRetrieveNVDA() {
        Optional<StockDTO> stock = stockInfoRetriever.findStockInfo("NVDA");

        assertTrue(stock.isPresent());
        log.info("Stock: {}", stock.get());
        assertEquals("NVDA", stock.get().ticker());
        assertEquals("NVIDIA Corporation (NVDA)", stock.get().description());
        assertTrue(stock.get().currentPE().compareTo(ZERO) > 0);
        assertTrue(stock.get().forwardPE().compareTo(ZERO) > 0);
        assertTrue(stock.get().PEG().compareTo(ZERO) > 0);
        assertTrue(stock.get().beta().compareTo(ZERO) > 0);
        assertTrue(stock.get().currentPrice().compareTo(ZERO) > 0);
        assertTrue(stock.get().marketCap().compareTo(ZERO) > 0);
        assertTrue(stock.get().priceToSales().compareTo(ZERO) > 0);
        assertTrue(stock.get().oneYearHigh().compareTo(ZERO) > 0);
        assertTrue(stock.get().oneYearLow().compareTo(ZERO) > 0);
        assertTrue(stock.get().oneYearHigh().compareTo(stock.get().oneYearLow()) > 0);
        assertNotNull(stock.get().oneYearChangePercentage());
        assertNotNull(stock.get().dividend()); //can actually be null if a company doesn't pay div, in this case it pays
        assertEquals("USD", stock.get().currency());
    }
}