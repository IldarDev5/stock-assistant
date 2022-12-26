package com.ildar.stockassistant.controller;

import com.ildar.stockassistant.dto.StockDataDTO;
import com.ildar.stockassistant.service.StockDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/stockData")
@RequiredArgsConstructor
public class StockDataController {

    private final StockDataService stockDataService;

    @GetMapping("/{ticker}")
    public Mono<StockDataDTO> findByTicker(@PathVariable String ticker) {
        return stockDataService.findByTicker(ticker);
    }
}
