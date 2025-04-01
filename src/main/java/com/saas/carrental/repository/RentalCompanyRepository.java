package com.saas.carrental.repository;

import com.saas.carrental.entity.RentalCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RentalCompanyRepository extends JpaRepository<RentalCompany, Long> {
    Optional<RentalCompany> findByEmail(String email);
    boolean existsByEmail(String email);
}