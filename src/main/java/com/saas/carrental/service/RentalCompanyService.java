package com.saas.carrental.service;

import com.saas.carrental.dto.auth.RentalCompanyRegistrationRequest;
import com.saas.carrental.entity.RentalCompany;
import com.saas.carrental.repository.RentalCompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RentalCompanyService {
    private final RentalCompanyRepository rentalCompanyRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public RentalCompany registerCompany(RentalCompanyRegistrationRequest request) {
        if (rentalCompanyRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email is already registered");
        }

        RentalCompany company = new RentalCompany();
        company.setName(request.getName());
        company.setEmail(request.getEmail());
        company.setPassword(passwordEncoder.encode(request.getPassword()));
        company.setPhoneNumber(request.getPhoneNumber());
        company.setAddress(request.getAddress());
        company.setCity(request.getCity());
        company.setState(request.getState());
        company.setZipCode(request.getZipCode());
        company.setCars(new HashSet<>());

        return rentalCompanyRepository.save(company);
    }

    @Transactional(readOnly = true)
    public Optional<RentalCompany> findByEmail(String email) {
        return rentalCompanyRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public RentalCompany getCompanyById(Long id) {
        return rentalCompanyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rental company not found"));
    }

    @Transactional(readOnly = true)
    public List<RentalCompany> getAllCompanies() {
        return rentalCompanyRepository.findAll();
    }

    @Transactional
    public RentalCompany updateCompany(Long id, RentalCompanyRegistrationRequest request) {
        RentalCompany company = getCompanyById(id);
        
        company.setName(request.getName());
        company.setPhoneNumber(request.getPhoneNumber());
        company.setAddress(request.getAddress());
        company.setCity(request.getCity());
        company.setState(request.getState());
        company.setZipCode(request.getZipCode());
        
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            company.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        return rentalCompanyRepository.save(company);
    }

    @Transactional
    public void deleteCompany(Long id) {
        if (!rentalCompanyRepository.existsById(id)) {
            throw new RuntimeException("Rental company not found");
        }
        rentalCompanyRepository.deleteById(id);
    }
}