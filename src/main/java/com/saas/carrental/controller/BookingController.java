package com.saas.carrental.controller;

import com.saas.carrental.dto.booking.BookingRequestDTO;
import com.saas.carrental.dto.booking.BookingResponseDTO;
import com.saas.carrental.entity.Booking;
import com.saas.carrental.entity.BookingStatus;
import com.saas.carrental.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@Tag(name = "Bookings", description = "Booking management APIs")
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Create new booking")
    public ResponseEntity<BookingResponseDTO> createBooking(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody BookingRequestDTO request) {
        // Extract user ID from authenticated user details
        Long userId = extractUserId(userDetails);
        return ResponseEntity.ok(bookingService.createBooking(userId, request));
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Get user's bookings")
    public ResponseEntity<List<Booking>> getUserBookings(
            @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = extractUserId(userDetails);
        return ResponseEntity.ok(bookingService.getUserBookings(userId));
    }

    @GetMapping("/company/{companyId}")
    @PreAuthorize("hasRole('RENTAL_COMPANY')")
    @Operation(summary = "Get company's bookings")
    public ResponseEntity<List<Booking>> getCompanyBookings(
            @PathVariable Long companyId) {
        return ResponseEntity.ok(bookingService.getCompanyBookings(companyId));
    }

    @GetMapping("/{bookingId}")
    @PreAuthorize("hasAnyRole('USER', 'RENTAL_COMPANY')")
    @Operation(summary = "Get booking details")
    public ResponseEntity<BookingResponseDTO> getBookingById(
            @PathVariable Long bookingId) {
        return ResponseEntity.ok(bookingService.getBookingById(bookingId));
    }

    @PutMapping("/{bookingId}/status")
    @PreAuthorize("hasRole('RENTAL_COMPANY')")
    @Operation(summary = "Update booking status")
    public ResponseEntity<BookingResponseDTO> updateBookingStatus(
            @PathVariable Long bookingId,
            @Parameter(description = "New booking status")
            @RequestParam BookingStatus status) {
        return ResponseEntity.ok(bookingService.updateBookingStatus(bookingId, status));
    }

    // Helper method to extract user ID from UserDetails
    private Long extractUserId(UserDetails userDetails) {
        // In a real application, you would implement this based on your UserDetails implementation
        // For example, you might have a custom UserDetails class that includes the user ID
        // This is just a placeholder implementation
        return Long.parseLong(userDetails.getUsername().split(":")[1]);
    }
}