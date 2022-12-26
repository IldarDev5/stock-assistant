package com.ildar.stockassistant.config;

import com.ildar.stockassistant.repository.StockDataRepository;
import com.ildar.stockassistant.service.ReactiveStockInfoRetriever;
import com.ildar.stockassistant.service.impl.YahooStockRetrieverImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackageClasses = StockDataRepository.class)
public class Config {

    @Bean
    public ReactiveStockInfoRetriever stockInfoRetriever() {
        return new YahooStockRetrieverImpl();
    }
}
