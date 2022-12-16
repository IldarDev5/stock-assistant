package com.ildar.stockassistant.service.impl;

import com.ildar.stockassistant.dto.StockDTO;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class YahooStockRetrieverImplTest {

    private final YahooStockRetrieverImpl stockInfoRetriever = new YahooStockRetrieverImpl();

    @Test
    public void testRetrieveNVDA() {
        Optional<StockDTO> stock = stockInfoRetriever.findStockInfo("NVDA");

        assertTrue(stock.isPresent());
        assertEquals("NVDA", stock.get().ticker());
        assertNotNull(stock.get().currentPE());
        assertNotNull(stock.get().forwardPE());
        assertNotNull(stock.get().PEG());
        assertNotNull(stock.get().beta());
    }
}