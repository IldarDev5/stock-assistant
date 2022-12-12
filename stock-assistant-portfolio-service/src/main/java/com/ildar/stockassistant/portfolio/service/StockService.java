package com.ildar.stockassistant.portfolio.service;

import com.ildar.stockassistant.portfolio.domain.Stock;
import com.ildar.stockassistant.portfolio.dto.StockDTO;
import com.ildar.stockassistant.portfolio.exception.StockDoesNotExistException;
import com.ildar.stockassistant.portfolio.exception.TickerAlreadyExistsException;
import com.ildar.stockassistant.portfolio.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockService {

    private final StockRepository stockRepository;

    public StockDTO createStock(StockDTO stockDTO) {
        if (isNotEmpty(stockRepository.findByTickerAndExchange(stockDTO.ticker(), stockDTO.exchange()))) {
            throw new TickerAlreadyExistsException(stockDTO);
        }

        Stock stock = Stock.builder()
                .ticker(stockDTO.ticker())
                .description(stockDTO.description())
                .exchange(stockDTO.exchange())
                .build();

        stockRepository.save(stock);
        log.info("Saved stock {} into DB.", stockDTO);

        return StockDTO.of(stock);
    }

    public List<StockDTO> readAll() {
        return stockRepository.findAll().stream()
                .map(StockDTO::of)
                .collect(Collectors.toList());
    }

    public StockDTO deleteStock(Long id) {
        Stock stockToDelete = stockRepository.findById(id)
                .orElseThrow(() -> new StockDoesNotExistException(id));
        stockRepository.delete(stockToDelete);
        return StockDTO.of(stockToDelete);
    }
}
