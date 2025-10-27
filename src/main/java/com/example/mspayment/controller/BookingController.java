package com.example.mspayment.controller;

import com.example.mspayment.dto.TicketOrderDto;
import com.example.mspayment.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/booking")
@RequiredArgsConstructor
@Slf4j
public class BookingController {

    private final ReservationService service;

    @PostMapping("/reserve")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> reserveSeat(@RequestBody TicketOrderDto order) {
        log.info("API call: reserveSeat for orderId={} userId={}", order.getTicketOrderId(), order.getUserId());
        service.reserveSeat(order);
        return ResponseEntity.ok("Reservation successful");
    }
}