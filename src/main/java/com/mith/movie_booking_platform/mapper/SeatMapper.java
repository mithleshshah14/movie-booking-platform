package com.mith.movie_booking_platform.mapper;

import com.mith.movie_booking_platform.entity.Seat;
import com.mith.movie_booking_platform.response.SeatResponse;
import org.mapstruct.Mapper;

/**
 * @author mithl
 * @date 21-02-2026
 * @email mithleshshah84@gmail.com
 */
@Mapper(componentModel = "spring")
public interface SeatMapper {

    SeatResponse entityToResponse(Seat seat);
}
