package com.ildar.stockassistant.portfolio.repository;

import com.ildar.stockassistant.portfolio.domain.StockPortfolio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockPortfolioRepository extends JpaRepository<StockPortfolio, Long> {
}
