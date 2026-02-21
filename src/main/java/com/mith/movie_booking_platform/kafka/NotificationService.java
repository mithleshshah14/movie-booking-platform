package com.mith.movie_booking_platform.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * @author mithl
 * @date 21-02-2026
 * @email mithleshshah84@gmail.com
 */
@Slf4j
@Service
public class NotificationService {

    @KafkaListener(topics = "booking-event", groupId = "notification-group")
    public void bookingEventListener(BookingEvent bookingEvent){
        log.info("Received Booking Event:{}",bookingEvent.getBookingId());
        sendEmailNotification(bookingEvent);
        sendSMSNotification(bookingEvent);

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
