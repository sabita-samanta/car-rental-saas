package com.saas.carrental.service;

import java.time.Duration;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saas.carrental.dto.booking.BookingRequestDTO;
import com.saas.carrental.dto.booking.BookingResponseDTO;
import com.saas.carrental.entity.Booking;
import com.saas.carrental.entity.BookingStatus;
import com.saas.carrental.entity.Car;
import com.saas.carrental.entity.CarStatus;
import com.saas.carrental.entity.User;
import com.saas.carrental.repository.BookingRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingService {
	private final BookingRepository bookingRepository;
	private final CarService carService;
	private final UserService userService;

	@Transactional
	public BookingResponseDTO createBooking(Long userId, BookingRequestDTO request) {
		// Validate booking dates
		if (request.getStartDate().isAfter(request.getEndDate())) {
			throw new RuntimeException("Start date must be before end date");
		}

		// Get car and check availability
		Car car = carService.getCarById(request.getCarId());
		if (car.getStatus() != CarStatus.AVAILABLE) {
			throw new RuntimeException("Car is not available for booking");
		}

		// Check for overlapping bookings
		List<Booking> overlappingBookings = bookingRepository.findOverlappingBookings(car.getId(),
				request.getStartDate(), request.getEndDate());
		if (!overlappingBookings.isEmpty()) {
			throw new RuntimeException("Car is already booked for the selected dates");
		}

		// Calculate total price
		long days = Duration.between(request.getStartDate(), request.getEndDate()).toDays();
		if (days < 1)
			days = 1; // Minimum one day charge
		double totalPrice = days * car.getPricePerDay();

		// Create booking
		User user = userService.getUserById(userId);
		Booking booking = new Booking();
		booking.setUser(user);
		booking.setCar(car);
		booking.setStartDate(request.getStartDate());
		booking.setEndDate(request.getEndDate());
		booking.setTotalPrice(totalPrice);
		booking.setStatus(BookingStatus.PENDING);
		booking.setSpecialRequests(request.getSpecialRequests());
		booking.setInsuranceRequired(request.getInsuranceRequired());
		booking.setPickupLocation(request.getPickupLocation());
		booking.setDropoffLocation(request.getDropoffLocation());

		booking = bookingRepository.save(booking);

		// Update car status
		carService.updateCarStatus(car.getId(), CarStatus.RENTED);

		return convertToResponseDTO(booking);
	}

	@Transactional(readOnly = true)
	public List<Booking> getUserBookings(Long userId) {
		User user = userService.getUserById(userId);
		return bookingRepository.findByUser(user);
	}

	@Transactional(readOnly = true)
	public List<Booking> getCompanyBookings(Long companyId) {
		return bookingRepository.findBookingsByCompany(companyId);
	}

	@Transactional
	public BookingResponseDTO updateBookingStatus(Long bookingId, BookingStatus status) {
		Booking booking = bookingRepository.findById(bookingId)
				.orElseThrow(() -> new RuntimeException("Booking not found"));

		booking.setStatus(status);

		// Update car status based on booking status
		if (status == BookingStatus.CANCELLED || status == BookingStatus.COMPLETED) {
			carService.updateCarStatus(booking.getCar().getId(), CarStatus.AVAILABLE);
		} else if (status == BookingStatus.CONFIRMED) {
			carService.updateCarStatus(booking.getCar().getId(), CarStatus.RENTED);
		}

		booking = bookingRepository.save(booking);
		return convertToResponseDTO(booking);
	}

	@Transactional(readOnly = true)
	public BookingResponseDTO getBookingById(Long bookingId) {
		Booking booking = bookingRepository.findById(bookingId)
				.orElseThrow(() -> new RuntimeException("Booking not found"));
		return convertToResponseDTO(booking);
	}

	private BookingResponseDTO convertToResponseDTO(Booking booking) {
		return BookingResponseDTO.builder().id(booking.getId()).userId(booking.getUser().getId())
				.userEmail(booking.getUser().getEmail())
				.userFullName(booking.getUser().getFirstName() + " " + booking.getUser().getLastName())
				.startDate(booking.getStartDate()).endDate(booking.getEndDate()).totalPrice(booking.getTotalPrice())
				.status(booking.getStatus()).specialRequests(booking.getSpecialRequests())
				.insuranceRequired(booking.getInsuranceRequired()).pickupLocation(booking.getPickupLocation())
				.dropoffLocation(booking.getDropoffLocation()).createdAt(booking.getCreatedAt())
				.updatedAt(booking.getUpdatedAt()).build();
	}
}