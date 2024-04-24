package com.domelchenko.webbank.controller;

import com.domelchenko.webbank.model.Accounts;
import com.domelchenko.webbank.repository.AccountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {
    @Autowired
    private AccountsRepository accountsRepository;

    @GetMapping("/myAccount")
    public Accounts getAccountDetails(@RequestParam int id) {
        return accountsRepository.findByCustomerId(id);
    }
}
