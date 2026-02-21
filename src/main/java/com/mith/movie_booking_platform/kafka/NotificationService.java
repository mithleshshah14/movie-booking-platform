package com.mith.movie_booking_platform.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * @author mithl
 * @date 21-02-2026
 * @email mithleshshah84@gmail.com
 */
@Slf4j
@Service
public class NotificationService {

    @KafkaListener(topics = "booking-events", groupId = "notification-group")
    public void bookingEventListener(String eventJson) {  // ✅ String, not BookingEvent
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            BookingEvent bookingEvent = objectMapper.readValue(eventJson, BookingEvent.class);

            log.info("Received Booking Event: {}", bookingEvent.getBookingId());
            sendEmailNotification(bookingEvent);
            sendSMSNotification(bookingEvent);
        } catch (Exception e) {
            log.error("Failed to process booking event", e);
        }
    }

    private void sendEmailNotification(BookingEvent event) {
        log.info("📧 Sending email to user: {}", event.getUserId());
        log.info("Subject: Booking Confirmed - {}", event.getMovieTitle());
        log.info("Booking ID: {}, Seats: {}, Total: ₹{}",
                event.getBookingId(),
                event.getSeatNumbers(),
                event.getTotalPrice()
        );
    }

    private void sendSMSNotification(BookingEvent event) {
        log.info("📱 Sending SMS to user: {}", event.getUserId());
        log.info("Your booking #{} is confirmed for {} at {}",
                event.getBookingId(),
                event.getMovieTitle(),
                event.getTheatreName()
        );
    }
}
