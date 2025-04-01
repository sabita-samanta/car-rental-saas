package com.saas.carrental.repository;

import com.saas.carrental.entity.Booking;
import com.saas.carrental.entity.BookingStatus;
import com.saas.carrental.entity.Car;
import com.saas.carrental.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUser(User user);
    
    List<Booking> findByCar(Car car);
    
    List<Booking> findByStatus(BookingStatus status);
    
    @Query("SELECT b FROM Booking b WHERE b.user.id = :userId AND " +
           "b.startDate >= :startDate ORDER BY b.startDate DESC")
    List<Booking> findUserFutureBookings(Long userId, LocalDateTime startDate);
    
    @Query("SELECT b FROM Booking b WHERE b.car.id = :carId AND " +
           "((b.startDate BETWEEN :startDate AND :endDate) OR " +
           "(b.endDate BETWEEN :startDate AND :endDate)) AND " +
           "b.status = 'CONFIRMED'")
    List<Booking> findOverlappingBookings(Long carId, LocalDateTime startDate, LocalDateTime endDate);
    
    @Query("SELECT b FROM Booking b WHERE b.car.company.id = :companyId " +
           "ORDER BY b.startDate DESC")
    List<Booking> findBookingsByCompany(Long companyId);
    
    List<Booking> findByUserAndStatusOrderByStartDateDesc(User user, BookingStatus status);
}