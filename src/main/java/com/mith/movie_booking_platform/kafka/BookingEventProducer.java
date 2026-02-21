package com.mith.movie_booking_platform.kafka;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * @author mithl
 * @date 21-02-2026
 * @email mithleshshah84@gmail.com
 */
@Slf4j
@AllArgsConstructor
@Service
public class BookingEventProducer {

    private KafkaTemplate<String, BookingEvent> kafkaTemplate;

    @Value("${kafka.topic}")
    private String topic;

    public void publishBookingEvent(BookingEvent bookingEvent) {
        log.info("Publishing booking event:{}", bookingEvent.getBookingId());
        kafkaTemplate.send(topic, bookingEvent);
        log.info("Booking event published successfully");
    }




}
