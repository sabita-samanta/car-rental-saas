package com.saas.carrental.dto.car;

import com.saas.carrental.entity.CarStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarDTO {
    private Long id;
    private Long companyId;
    private String companyName;
    private String make;
    private String model;
    private Integer modelYear;
    private String licensePlate;
    private Double pricePerDay;
    @Builder.Default
    private CarStatus status = CarStatus.AVAILABLE;
    private String imageUrl;
    private String description;
}