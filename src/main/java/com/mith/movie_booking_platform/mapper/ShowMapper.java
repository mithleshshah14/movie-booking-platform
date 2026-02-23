package com.mith.movie_booking_platform.mapper;

import com.mith.movie_booking_platform.entity.Show;
import com.mith.movie_booking_platform.response.ShowResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author mithl
 * @date 21-02-2026
 * @email mithleshshah84@gmail.com
 */
@Mapper(componentModel = "spring")
public interface ShowMapper {

    @Mapping(source = "id", target = "showId")
    @Mapping(source = "movie.title", target = "movieTitle")
    @Mapping(source = "movie.language", target = "language")
    @Mapping(source = "movie.genre", target = "genre")
    @Mapping(source = "theatre.name", target = "theatreName")
    @Mapping(source = "theatre.city", target = "city")
    @Mapping(source = "theatre.address", target = "theatreAddress")
    @Mapping(target = "availableSeats", ignore = true)
    ShowResponse entityToResponse(Show show);
}
