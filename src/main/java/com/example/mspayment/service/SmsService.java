package com.example.mspayment.service;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SmsService {

    @Value("${twilio.from-number}")
    private String fromNumber;

    public void sendSms(String toPhoneNumber, String message) {
        try {
            log.info("📲 Sending SMS to {}: {}", toPhoneNumber, message);

            Message.creator(
                    new PhoneNumber(toPhoneNumber),
                    new PhoneNumber(fromNumber),
                    message
            ).create();

            log.info("✅ SMS sent successfully to {}", toPhoneNumber);
        } catch (Exception e) {
            log.error("❌ Failed to send SMS to {}. Error: {}", toPhoneNumber, e.getMessage());
        }
    }
}
