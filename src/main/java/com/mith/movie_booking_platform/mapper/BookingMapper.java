package com.mith.movie_booking_platform.mapper;

import com.mith.movie_booking_platform.entity.Booking;
import com.mith.movie_booking_platform.entity.Seat;
import com.mith.movie_booking_platform.response.BookingResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

/**
 * @author mithl
 * @date 21-02-2026
 * @email mithleshshah84@gmail.com
 */
@Mapper(componentModel = "spring")
public interface BookingMapper {

    @Mapping(source = "id", target = "bookingId")
    @Mapping(source = "show.id", target = "showId")
    @Mapping(source = "show.movie.title", target = "movieTitle")
    @Mapping(source = "show.theatre.name", target = "theatreName")
    @Mapping(source = "show.theatre.city", target = "city")
    @Mapping(source = "show.theatre.address", target = "theatreAddress")
    @Mapping(source = "show.showDate", target = "showDate")
    @Mapping(source = "show.showTime", target = "showTime")
    @Mapping(source = "seats", target = "seatNumbers", qualifiedByName = "seatsToSeatNumbers")
    BookingResponse entityToResponse(Booking booking);

    @Named("seatsToSeatNumbers")
    default List<String> seatsToSeatNumbers(List<Seat> seats) {
        return seats.stream()
                .map(Seat::getSeatNumber)
                .toList();
    }

}
