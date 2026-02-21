package com.mith.movie_booking_platform.kafka;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * @author mithl
 * @date 21-02-2026
 * @email mithleshshah84@gmail.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingEvent{

    private Long bookingId;
    private String userId;
    private String movieTitle;
    private String theatreName;
    private LocalDate showDate;
    private LocalTime showTime;
    private List<String> seatNumbers;
    private Double totalPrice;
    private LocalDateTime bookingTime;
}
