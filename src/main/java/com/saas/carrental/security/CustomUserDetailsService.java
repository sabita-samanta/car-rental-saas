package com.saas.carrental.security;

import com.saas.carrental.entity.RentalCompany;
import com.saas.carrental.entity.User;
import com.saas.carrental.service.RentalCompanyService;
import com.saas.carrental.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;
    private  final RentalCompanyService rentalCompanyService;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOpt = userService.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            return org.springframework.security.core.userdetails.User
                    .withUsername(user.getEmail())
                    .password(user.getPassword())
                    .authorities(user.getRoles().stream()
                            .map(SimpleGrantedAuthority::new)
                            .toList())
                    .build();
        }

        Optional<RentalCompany> companyOpt = rentalCompanyService.findByEmail(email);
        if (companyOpt.isPresent()) {
            RentalCompany company = companyOpt.get();
            return org.springframework.security.core.userdetails.User
                    .withUsername(company.getEmail())
                    .password(company.getPassword())
                    .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_RENTAL_COMPANY")))
                    .build();
        }

        throw new UsernameNotFoundException("User not found with email: " + email);
    }
}