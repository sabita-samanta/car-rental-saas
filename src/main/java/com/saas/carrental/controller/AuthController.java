package com.saas.carrental.controller;

import com.saas.carrental.dto.auth.*;
import com.saas.carrental.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication management APIs")
public class AuthController {

    private  final AuthService authService;

    @PostMapping("/login")
    @Operation(
        summary = "Authenticate user",
        description = "Authenticate a user or rental company and return JWT token"
    )
    public ResponseEntity<JwtAuthenticationResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.authenticateUser(loginRequest));
    }

    @PostMapping("/register/user")
    @Operation(
        summary = "Register new user",
        description = "Register a new user account and return JWT token"
    )
    public ResponseEntity<JwtAuthenticationResponse> registerUser(
            @Valid @RequestBody UserRegistrationRequest request) {
        return ResponseEntity.ok(authService.registerUser(request));
    }

    @PostMapping("/register/company")
    @Operation(
        summary = "Register new rental company",
        description = "Register a new rental company account and return JWT token"
    )
    public ResponseEntity<JwtAuthenticationResponse> registerRentalCompany(
            @Valid @RequestBody RentalCompanyRegistrationRequest request) {
        return ResponseEntity.ok(authService.registerRentalCompany(request));
    }
}