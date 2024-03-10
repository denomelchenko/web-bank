package com.domelchenko.webbank.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "mobile_number", nullable = false, length = 20)
    private String mobileNumber;

    @Column(name = "pwd", nullable = false, length = 500)
    private String pwd;

    @Column(name = "role", nullable = false, length = 100)
    private String role;

    @Column(name = "create_dt")
    private LocalDate createDt;

    @OneToMany(mappedBy = "customer")
    private Set<AccountTransactions> accountTransactions = new LinkedHashSet<>();

    @OneToMany(mappedBy = "customer")
    private Set<Accounts> accounts = new LinkedHashSet<>();

    @OneToMany(mappedBy = "customer")
    private Set<Cards> cards = new LinkedHashSet<>();

    @OneToMany(mappedBy = "customer")
    private Set<Loans> loans = new LinkedHashSet<>();

}