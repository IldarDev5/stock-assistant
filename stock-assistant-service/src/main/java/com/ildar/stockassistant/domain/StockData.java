package com.ildar.stockassistant.domain;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("stock_data")
@Builder
public record StockData(
        @Id String id
        //todo
) { }
