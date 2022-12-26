package com.ildar.stockassistant.repository;

import com.ildar.stockassistant.domain.StockData;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface StockDataRepository extends ReactiveMongoRepository<StockData, String> {
}
