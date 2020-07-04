package com.victor.technest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Currency;

@Entity
@Data
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Currency currency;

    @Column(precision = 15, scale = 2, nullable = false)
    private BigDecimal balance;

    @Column(nullable = false, updatable = false)
    private Boolean treasury;
}
