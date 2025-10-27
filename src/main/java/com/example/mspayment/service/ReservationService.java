package com.example.mspayment.service;


import com.example.mspayment.dto.TicketOrderDto;
import com.example.mspayment.model.Reservation;
import com.example.mspayment.model.enums.ReservationStatus;
import com.example.mspayment.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationService {

    private final ReservationRepository repository;
    private final RabbitTemplate rabbitTemplate;

    private static final String NOTIFICATION_EXCHANGE = "notification-exchange";
    private static final String NOTIFICATION_ROUTING_KEY = "notification.routing.key";

    @Transactional
    public void reserveSeat(TicketOrderDto order) {
        log.info("Reserving {} seats for orderId={} userId={}", order.getQuantity(), order.getTicketOrderId(), order.getUserId());

        for (int i = 0; i < order.getQuantity(); i++) {
            int seatNumber = i + 1;
            if (repository.existsByEventIdAndSeatNumber(order.getEventId(), seatNumber)) {
                throw new IllegalArgumentException("Seat " + seatNumber + " is already reserved");
            }

            Reservation reservation = Reservation.builder()
                    .ticketOrderId(order.getTicketOrderId())
                    .userId(order.getUserId())
                    .eventId(order.getEventId())
                    .seatNumber(seatNumber)
                    .status(ReservationStatus.CONFIRMED)
                    .build();

            repository.save(reservation);
        }

        log.info("Reservation completed for orderId={} userId={}", order.getTicketOrderId(), order.getUserId());

        rabbitTemplate.convertAndSend(NOTIFICATION_EXCHANGE, NOTIFICATION_ROUTING_KEY, order);
        log.info("Notification event sent to RabbitMQ for orderId={} userId={}", order.getTicketOrderId(), order.getUserId());
    }
}
