package com.domelchenko.webbank.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "cards", indexes = {
        @Index(name = "customer_id", columnList = "customer_id")
})
public class Cards {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id", nullable = false)
    private Integer id;

    @Column(name = "card_number", nullable = false, length = 100)
    private String cardNumber;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "card_type", nullable = false, length = 100)
    private String cardType;

    @Column(name = "total_limit", nullable = false)
    private Integer totalLimit;

    @Column(name = "amount_used", nullable = false)
    private Integer amountUsed;

    @Column(name = "available_amount", nullable = false)
    private Integer availableAmount;

    @Column(name = "create_dt")
    private LocalDate createDt;

}