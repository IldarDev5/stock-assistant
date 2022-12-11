package com.ildar.stockassistant.portfolio.repository;

import com.ildar.stockassistant.portfolio.domain.StockFavourite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockFavouriteRepository extends JpaRepository<StockFavourite, Long> {
}
