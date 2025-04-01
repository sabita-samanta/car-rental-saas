package com.saas.carrental.dto.booking;

import com.saas.carrental.dto.car.CarDTO;
import com.saas.carrental.entity.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponseDTO {
    private Long id;
    private Long userId;
    private String userName;
    private String userEmail;
    private String userFullName;
    private CarDTO car;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Double totalPrice;
    private BookingStatus status;
    private String specialRequests;
    private Boolean insuranceRequired;
    private String pickupLocation;
    private String dropoffLocation;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}