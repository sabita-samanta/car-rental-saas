package com.saas.carrental.repository;

import com.saas.carrental.entity.Car;
import com.saas.carrental.entity.CarStatus;
import com.saas.carrental.entity.RentalCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByCompany(RentalCompany company);
    List<Car> findByStatus(CarStatus status);
    
    @Query("SELECT c FROM Car c WHERE c.status = 'AVAILABLE' AND c.company.id = :companyId")
    List<Car> findAvailableCarsByCompany(Long companyId);
    
    @Query("SELECT c FROM Car c WHERE c.status = 'AVAILABLE' " +
           "AND c.id NOT IN (SELECT b.car.id FROM Booking b " +
           "WHERE (b.startDate BETWEEN :startDate AND :endDate " +
           "OR b.endDate BETWEEN :startDate AND :endDate) " +
           "AND b.status = 'CONFIRMED')")
    List<Car> findAvailableCarsForDateRange(LocalDateTime startDate, LocalDateTime endDate);
    
    List<Car> findByMakeAndModel(String make, String model);
    
    List<Car> findByPricePerDayLessThanEqual(Double maxPrice);
}