package com.example.mspayment.repository;


import com.example.mspayment.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    boolean existsByEventIdAndSeatNumber(Long eventId, int seatNumber);
}