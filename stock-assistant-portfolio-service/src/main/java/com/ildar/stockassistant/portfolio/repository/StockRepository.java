package com.ildar.stockassistant.portfolio.repository;

import com.ildar.stockassistant.portfolio.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {
}
