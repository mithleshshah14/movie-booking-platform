package com.mith.movie_booking_platform.mapper;

import com.mith.movie_booking_platform.entity.Booking;
import com.mith.movie_booking_platform.response.BookingResponse;
import org.mapstruct.Mapper;

/**
 * @author mithl
 * @date 21-02-2026
 * @email mithleshshah84@gmail.com
 */
@Mapper
public interface BookingMapper {

    BookingResponse entityToResponse(Booking booking);

}
