package com.ildar.stockassistant.portfolio.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "stock")
@Builder
@AllArgsConstructor @NoArgsConstructor
public class Stock {

    @Id
    @Column
    @SequenceGenerator(name = "stock_seq", allocationSize = 10)
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "stock_seq"
    )
    private Long id;

    @Column(nullable = false)
    private String ticker;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String exchange;

    @Column
    private BigDecimal lastPrice;

    @Column
    private LocalDateTime lastPriceDate;
}
