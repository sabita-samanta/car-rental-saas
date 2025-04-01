package com.saas.carrental.service;

import com.saas.carrental.dto.car.CarDTO;
import com.saas.carrental.entity.Car;
import com.saas.carrental.entity.CarStatus;
import com.saas.carrental.entity.RentalCompany;
import com.saas.carrental.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;
    private final RentalCompanyService rentalCompanyService;

    @Transactional
    public Car addCar(Long companyId, CarDTO carDTO) {
        RentalCompany company = rentalCompanyService.getCompanyById(companyId);
        
        Car car = new Car();
        car.setCompany(company);
        car.setMake(carDTO.getMake());
        car.setModel(carDTO.getModel());
        car.setModelYear(carDTO.getModelYear());
        car.setLicensePlate(carDTO.getLicensePlate());
        car.setPricePerDay(carDTO.getPricePerDay());
        car.setStatus(CarStatus.AVAILABLE);

        return carRepository.save(car);
    }

    @Transactional(readOnly = true)
    public List<Car> getAvailableCars() {
        return carRepository.findByStatus(CarStatus.AVAILABLE);
    }

    @Transactional(readOnly = true)
    public List<Car> searchAvailableCars(LocalDateTime startDate, LocalDateTime endDate) {
        return carRepository.findAvailableCarsForDateRange(startDate, endDate);
    }

    @Transactional(readOnly = true)
    public List<Car> getCompanyCars(Long companyId) {
        RentalCompany company = rentalCompanyService.getCompanyById(companyId);
        return carRepository.findByCompany(company);
    }

    @Transactional(readOnly = true)
    public Car getCarById(Long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found"));
    }

    @Transactional
    public Car updateCar(Long id, CarDTO carDTO) {
        Car car = getCarById(id);
        
        car.setMake(carDTO.getMake());
        car.setModel(carDTO.getModel());
        car.setModelYear(carDTO.getModelYear());
        car.setLicensePlate(carDTO.getLicensePlate());
        car.setPricePerDay(carDTO.getPricePerDay());
        
        if (carDTO.getStatus() != null) {
            car.setStatus(carDTO.getStatus());
        }

        return carRepository.save(car);
    }

    @Transactional
    public void deleteCar(Long id) {
        if (!carRepository.existsById(id)) {
            throw new RuntimeException("Car not found");
        }
        carRepository.deleteById(id);
    }

    @Transactional
    public void updateCarStatus(Long id, CarStatus status) {
        Car car = getCarById(id);
        car.setStatus(status);
        carRepository.save(car);
    }

    @Transactional(readOnly = true)
    public List<Car> searchCars(String make, String model, Double maxPrice) {
        List<Car> cars = carRepository.findByStatus(CarStatus.AVAILABLE);
        
        return cars.stream()
                .filter(car -> make == null || car.getMake().equalsIgnoreCase(make))
                .filter(car -> model == null || car.getModel().equalsIgnoreCase(model))
                .filter(car -> maxPrice == null || car.getPricePerDay() <= maxPrice)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Car> getAvailableCarsByCompany(Long companyId) {
        return carRepository.findAvailableCarsByCompany(companyId);
    }
}