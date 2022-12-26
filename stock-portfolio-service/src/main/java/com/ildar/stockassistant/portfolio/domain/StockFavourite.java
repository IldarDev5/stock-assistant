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
@Table(name = "stock_favourite")
public class StockFavourite {

    @Id
    @Column
    @SequenceGenerator(name = "stock_favourite_seq", allocationSize = 10)
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "stock_favourite_seq"
    )
    private Long id;

    @ManyToOne(optional = false)
    private Stock stock;

    @Column(nullable = false, name = "username")
    private String user;

    @Column
    private BigDecimal targetedPrice;
}
