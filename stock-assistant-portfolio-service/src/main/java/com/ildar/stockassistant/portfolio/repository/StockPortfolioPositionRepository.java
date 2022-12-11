package com.ildar.stockassistant.portfolio.repository;

import com.ildar.stockassistant.portfolio.domain.StockPortfolioPosition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockPortfolioPositionRepository extends JpaRepository<StockPortfolioPosition, Long> {
}
