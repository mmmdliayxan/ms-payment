package com.example.mspayment.listener;

import com.example.mspayment.config.RabbitConfig;
import com.example.mspayment.dto.TicketOrderDto;
import com.example.mspayment.service.SmsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationListener {

    private final SmsService smsService;

    @RabbitListener(queues = RabbitConfig.QUEUE_NAME)
    public void handleNotification(TicketOrderDto order) {
        log.info("📩 Notification received for userId={} orderId={}", order.getUserId(), order.getTicketOrderId());

        String message = String.format(
                "Salam %s! Sifarişiniz təsdiqləndi 🎫 Tədbir ID: %s | Bilet sayı: %d | SmartEvent komandası sizə uğurlar arzulayır!",
                order.getUserId(), order.getEventId(), order.getQuantity()
        );

        smsService.sendSms(order.getUserPhone(), message);
    }
}