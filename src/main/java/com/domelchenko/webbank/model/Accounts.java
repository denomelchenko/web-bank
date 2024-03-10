package com.domelchenko.webbank.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "accounts", indexes = {
        @Index(name = "customer_id", columnList = "customer_id")
})
public class Accounts {
    @Id
    @Column(name = "account_number", nullable = false)
    private Integer accountNumber;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "account_type", nullable = false, length = 100)
    private String accountType;

    @Column(name = "branch_address", nullable = false, length = 200)
    private String branchAddress;

    @Column(name = "create_dt")
    private LocalDate createDt;

    @OneToMany(mappedBy = "accountNumber")
    private Set<AccountTransactions> accountTransactions = new LinkedHashSet<>();

}