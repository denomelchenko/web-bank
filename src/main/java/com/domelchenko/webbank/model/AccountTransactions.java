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
@Table(name = "account_transactions", indexes = {
        @Index(name = "account_number", columnList = "account_number"),
        @Index(name = "customer_id", columnList = "customer_id")
})
public class AccountTransactions {
    @Id
    @Column(name = "transaction_id", nullable = false, length = 200)
    private String transactionId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "account_number", nullable = false)
    private Accounts accountNumber;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "transaction_dt", nullable = false)
    private LocalDate transactionDt;

    @Column(name = "transaction_summary", nullable = false, length = 200)
    private String transactionSummary;

    @Column(name = "transaction_type", nullable = false, length = 100)
    private String transactionType;

    @Column(name = "transaction_amt", nullable = false)
    private Integer transactionAmt;

    @Column(name = "closing_balance", nullable = false)
    private Integer closingBalance;

    @Column(name = "create_dt")
    private LocalDate createDt;

}