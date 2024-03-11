package com.domelchenko.webbank.config;

import com.domelchenko.webbank.model.Authority;
import com.domelchenko.webbank.model.Customer;
import com.domelchenko.webbank.repository.CustomerRepository;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class WebBankUsernamePwdAuthenticationProvider implements AuthenticationProvider {
    private final CustomerRepository customerRepository;

    private final PasswordEncoder passwordEncoder;

    public WebBankUsernamePwdAuthenticationProvider(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.customerRepository = customerRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        List<Customer> customer = customerRepository.findByEmail(username);
        if (!customer.isEmpty() && passwordEncoder.matches(password, customer.get(0).getPwd())) {
            return new UsernamePasswordAuthenticationToken(
                    username,
                    password,
                    getGrantedAuthorities(customer.get(0).getAuthorities())
            );
        }
        throw new BadCredentialsException("Bad credentials");
    }

    private List<GrantedAuthority> getGrantedAuthorities(Set<Authority> authorities) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Authority authority :
                authorities) {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority.getName()));
        }
        return grantedAuthorities;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
