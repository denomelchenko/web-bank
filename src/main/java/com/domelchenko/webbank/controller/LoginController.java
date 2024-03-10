package com.domelchenko.webbank.controller;

import com.domelchenko.webbank.model.Customer;
import com.domelchenko.webbank.repository.CustomerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    private final CustomerRepository customerRepository;

    private final PasswordEncoder passwordEncoder;

    public LoginController(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Customer customer) {
        try {
            customer.setPwd(passwordEncoder.encode(customer.getPwd()));
            customerRepository.save(customer);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("User successfully created");

        } catch (Exception exception) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An exception occurred due to " + exception);
        }
    }
}
