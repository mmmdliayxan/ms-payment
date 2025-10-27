package com.example.mspayment.model;

import com.example.mspayment.model.enums.ReservationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reservations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long ticketOrderId;
    private Long userId;
    private Long eventId;

    private int seatNumber;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;
}