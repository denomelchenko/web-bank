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
@Table(name = "loans", indexes = {
        @Index(name = "customer_id", columnList = "customer_id")
})
public class Loans {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loan_number", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "start_dt", nullable = false)
    private LocalDate startDt;

    @Column(name = "loan_type", nullable = false, length = 100)
    private String loanType;

    @Column(name = "total_loan", nullable = false)
    private Integer totalLoan;

    @Column(name = "amount_paid", nullable = false)
    private Integer amountPaid;

    @Column(name = "outstanding_amount", nullable = false)
    private Integer outstandingAmount;

    @Column(name = "create_dt")
    private LocalDate createDt;

}