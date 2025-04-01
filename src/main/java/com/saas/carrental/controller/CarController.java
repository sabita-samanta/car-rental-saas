package com.saas.carrental.controller;

import com.saas.carrental.dto.car.CarDTO;
import com.saas.carrental.entity.Car;
import com.saas.carrental.entity.CarStatus;
import com.saas.carrental.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor
@Tag(name = "Cars", description = "Car management APIs")
@SecurityRequirement(name = "Bearer Authentication")
public class CarController {

    private  final CarService carService;

    @GetMapping
    @Operation(summary = "Get all available cars")
    public ResponseEntity<List<Car>> getAvailableCars() {
        return ResponseEntity.ok(carService.getAvailableCars());
    }

    @GetMapping("/search")
    @Operation(summary = "Search cars by criteria")
    public ResponseEntity<List<Car>> searchCars(
            @Parameter(description = "Car make") @RequestParam(required = false) String make,
            @Parameter(description = "Car model") @RequestParam(required = false) String model,
            @Parameter(description = "Maximum price per day") @RequestParam(required = false) Double maxPrice) {
        return ResponseEntity.ok(carService.searchCars(make, model, maxPrice));
    }

    @GetMapping("/available")
    @Operation(summary = "Search available cars for date range")
    public ResponseEntity<List<Car>> searchAvailableCars(
            @Parameter(description = "Start date (yyyy-MM-dd'T'HH:mm:ss)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Parameter(description = "End date (yyyy-MM-dd'T'HH:mm:ss)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return ResponseEntity.ok(carService.searchAvailableCars(startDate, endDate));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get car by ID")
    public ResponseEntity<Car> getCarById(@PathVariable Long id) {
        return ResponseEntity.ok(carService.getCarById(id));
    }

    @GetMapping("/company/{companyId}")
    @Operation(summary = "Get company's cars")
    public ResponseEntity<List<Car>> getCompanyCars(@PathVariable Long companyId) {
        return ResponseEntity.ok(carService.getCompanyCars(companyId));
    }

    @PostMapping("/company/{companyId}")
    @PreAuthorize("hasRole('RENTAL_COMPANY')")
    @Operation(summary = "Add new car")
    public ResponseEntity<Car> addCar(
            @PathVariable Long companyId,
            @Valid @RequestBody CarDTO carDTO) {
        return ResponseEntity.ok(carService.addCar(companyId, carDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('RENTAL_COMPANY')")
    @Operation(summary = "Update car details")
    public ResponseEntity<Car> updateCar(
            @PathVariable Long id,
            @Valid @RequestBody CarDTO carDTO) {
        return ResponseEntity.ok(carService.updateCar(id, carDTO));
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('RENTAL_COMPANY')")
    @Operation(summary = "Update car status")
    public ResponseEntity<Void> updateCarStatus(
            @PathVariable Long id,
            @RequestParam CarStatus status) {
        carService.updateCarStatus(id, status);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('RENTAL_COMPANY')")
    @Operation(summary = "Delete car")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/company/{companyId}/available")
    @Operation(summary = "Get company's available cars")
    public ResponseEntity<List<Car>> getAvailableCarsByCompany(@PathVariable Long companyId) {
        return ResponseEntity.ok(carService.getAvailableCarsByCompany(companyId));
    }
}