package com.mith.movie_booking_platform.service;

import com.mith.movie_booking_platform.response.SeatResponse;
import com.mith.movie_booking_platform.response.ShowResponse;

import java.time.LocalDate;
import java.util.List;

/**
 * @author mithl
 * @date 20-02-2026
 * @email mithleshshah84@gmail.com
 */
public interface ShowService {

    List<ShowResponse> getShows(Long movieId, String city, LocalDate date);

    List<SeatResponse> getSeatsForShow(Long showId);
}
