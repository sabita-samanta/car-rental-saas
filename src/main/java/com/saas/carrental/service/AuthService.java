package com.saas.carrental.service;

import java.util.Collections;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saas.carrental.dto.auth.JwtAuthenticationResponse;
import com.saas.carrental.dto.auth.LoginRequest;
import com.saas.carrental.dto.auth.RentalCompanyRegistrationRequest;
import com.saas.carrental.dto.auth.UserRegistrationRequest;
import com.saas.carrental.entity.RentalCompany;
import com.saas.carrental.entity.User;
import com.saas.carrental.security.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	private final UserService userService;
	private final RentalCompanyService rentalCompanyService;
	private final JwtTokenProvider tokenProvider;
	private final AuthenticationManager authenticationManager;

	public JwtAuthenticationResponse authenticateUser(LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

		String token = tokenProvider.generateToken(authentication);
		String email = loginRequest.getEmail();

		Optional<User> userOpt = userService.findByEmail(email);
		if (userOpt.isPresent()) {
			User user = userOpt.get();
			return JwtAuthenticationResponse.builder().token(token).id(user.getId()).email(user.getEmail())
					.firstName(user.getFirstName()).lastName(user.getLastName()).phoneNumber(user.getPhoneNumber())
					.roles(user.getRoles()).userType("USER").build();
		}

		Optional<RentalCompany> companyOpt = rentalCompanyService.findByEmail(email);
		if (companyOpt.isPresent()) {
			RentalCompany company = companyOpt.get();
			return JwtAuthenticationResponse.builder().token(token).id(company.getId()).email(company.getEmail())
					.companyName(company.getName()).phoneNumber(company.getPhoneNumber()).address(company.getAddress())
					.city(company.getCity()).state(company.getState()).zipCode(company.getZipCode())
					.roles(Collections.singleton("ROLE_RENTAL_COMPANY")).userType("RENTAL_COMPANY").build();
		}

		throw new RuntimeException("User not found with email: " + email);
	}

	@Transactional
	public JwtAuthenticationResponse registerUser(UserRegistrationRequest request) {
		User user = userService.registerUser(request);
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

		String token = tokenProvider.generateToken(authentication);
		return JwtAuthenticationResponse.builder().token(token).id(user.getId()).email(user.getEmail())
				.firstName(user.getFirstName()).lastName(user.getLastName()).phoneNumber(user.getPhoneNumber())
				.roles(user.getRoles()).userType("USER").build();
	}

	@Transactional
	public JwtAuthenticationResponse registerRentalCompany(RentalCompanyRegistrationRequest request) {
		RentalCompany company = rentalCompanyService.registerCompany(request);
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

		String token = tokenProvider.generateToken(authentication);
		return JwtAuthenticationResponse.builder().token(token).id(company.getId()).email(company.getEmail())
				.companyName(company.getName()).phoneNumber(company.getPhoneNumber()).address(company.getAddress())
				.city(company.getCity()).state(company.getState()).zipCode(company.getZipCode())
				.roles(Collections.singleton("ROLE_RENTAL_COMPANY")).userType("RENTAL_COMPANY").build();
	}
}