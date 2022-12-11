package com.ildar.stockassistant.portfolio.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.List;

@Data
@Entity
@Table(name = "stock_portfolio")
public class StockPortfolio {

    @Id
    @Column
    @SequenceGenerator(name = "stock_portfolio_seq", allocationSize = 10)
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "stock_portfolio_seq"
    )
    private Long id;

    @Column(nullable = false, name = "username")
    private String user;

    @OneToMany
    private List<StockPortfolioPosition> positions;
}
