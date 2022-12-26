package com.ildar.stockassistant.portfolio.repository;

import com.ildar.stockassistant.portfolio.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Long> {
    List<Stock> findByTickerAndExchange(String ticker, String exchange);
}
