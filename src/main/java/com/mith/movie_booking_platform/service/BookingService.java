package com.mith.movie_booking_platform.service;

import com.mith.movie_booking_platform.request.BookingRequest;
import com.mith.movie_booking_platform.response.BookingResponse;

/**
 * @author mithl
 * @date 20-02-2026
 * @email mithleshshah84@gmail.com
 */
public interface BookingService {

    BookingResponse bookShow(BookingRequest bookingRequest);
}
