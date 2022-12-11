package com.ildar.stockassistant.portfolio.controller;

import com.ildar.stockassistant.portfolio.dto.StockDTO;
import com.ildar.stockassistant.portfolio.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/stocks")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @GetMapping
    public List<StockDTO> getAllStocks() {
        return stockService.readAll();
    }

    @PostMapping
    public ResponseEntity<StockDTO> saveStock(@RequestBody @Valid StockDTO stockDTO) {
        stockService.createStock(stockDTO);
        return ResponseEntity.ok(stockDTO);
    }
}
