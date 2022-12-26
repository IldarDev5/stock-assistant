package com.ildar.stockassistant.portfolio.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "stock_portfolio_position")
public class StockPortfolioPosition {

    @Id
    @Column
    @SequenceGenerator(name = "stock_position_seq", allocationSize = 10)
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "stock_position_seq"
    )
    private Long id;

    @ManyToOne(optional = false)
    private Stock stock;

    @ManyToOne(optional = false)
    private StockPortfolio portfolio;

    @Column(nullable = false)
    private BigDecimal currentPosition;
}
