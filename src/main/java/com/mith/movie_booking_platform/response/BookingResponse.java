package com.mith.movie_booking_platform.response;

import com.mith.movie_booking_platform.enums.BookingStatus;
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
 * @date 20-02-2026
 * @email mithleshshah84@gmail.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingResponse {

    private Long bookingId;

    private String userId;

    private Long showId;

    private String movieTitle;

    private String theatreName;

    private String city;

    private String theatreAddress;

    private LocalDate showDate;

    private LocalTime showTime;

    private List<String> seatNumbers;

    private Double totalPrice;

    private LocalDateTime bookingTime;

    private BookingStatus status;
}