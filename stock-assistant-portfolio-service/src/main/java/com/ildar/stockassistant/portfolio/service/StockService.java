package com.ildar.stockassistant.portfolio.service;

import com.ildar.stockassistant.portfolio.domain.Stock;
import com.ildar.stockassistant.portfolio.dto.StockDTO;
import com.ildar.stockassistant.portfolio.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockService {

    private final StockRepository stockRepository;

    public void createStock(StockDTO stockDTO) {
        Stock stock = Stock.builder()
                .ticker(stockDTO.ticker())
                .description(stockDTO.description())
                .exchange(stockDTO.exchange())
                .build();

        stockRepository.save(stock);
        log.info("Saved stock {} into DB.", stockDTO);
    }

    public List<StockDTO> readAll() {
        return stockRepository.findAll().stream()
                .map(StockDTO::of)
                .collect(Collectors.toList());
    }
}
