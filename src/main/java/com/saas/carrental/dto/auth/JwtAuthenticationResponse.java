package com.saas.carrental.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthenticationResponse {
    private Long id;
    private String token;
    @Builder.Default
    private String type = "Bearer";
    private String email;
    private Set<String> roles;
    private String firstName;
    private String lastName;
    private String companyName;
    private String name;
    private String phoneNumber;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String userType;
}