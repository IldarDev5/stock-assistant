package com.ildar.stockassistant.portfolio.config;

import com.ildar.stockassistant.portfolio.repository.StockRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackageClasses = StockRepository.class)
public class Config {

}
