package com.mith.movie_booking_platform.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author mithl
 * @date 20-02-2026
 * @email mithleshshah84@gmail.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShowResponse {

    private Long showId;

    private String movieTitle;

    private String language;

    private String genre;

    private String theatreName;

    private String city;

    private String theatreAddress;

    private LocalDate showDate;

    private LocalTime showTime;

    private Double price;

    private Integer totalSeats;

    private Integer availableSeats;
}