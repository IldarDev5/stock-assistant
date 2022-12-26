package com.ildar.stockassistant.domain;

import com.ildar.stockassistant.dto.StockDTO;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Document("stock_data")
@Builder
public record StockData(
        @Id String id,
        String ticker,
        String description,
        String exchange,
        BigDecimal currentPE,
        BigDecimal forwardPE,
        BigDecimal PEG,
        BigDecimal beta,
        LocalDateTime lastTimeUpdated,
        Long countOfTimesRequested) {

    public static StockData from(StockDTO stockDTO) {
        return StockData.builder()
                .ticker(stockDTO.ticker())
                .exchange(stockDTO.exchange())
                .description(stockDTO.description())
                .currentPE(stockDTO.currentPE())
                .forwardPE(stockDTO.forwardPE())
                .beta(stockDTO.beta())
                .PEG(stockDTO.PEG())
                .build();
    }
}
