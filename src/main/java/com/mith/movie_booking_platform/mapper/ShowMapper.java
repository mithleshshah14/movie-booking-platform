package com.mith.movie_booking_platform.mapper;

import com.mith.movie_booking_platform.entity.Show;
import com.mith.movie_booking_platform.response.ShowResponse;
import org.mapstruct.Mapper;

/**
 * @author mithl
 * @date 21-02-2026
 * @email mithleshshah84@gmail.com
 */
@Mapper
public interface ShowMapper {

    ShowResponse entityToResponse(Show show);
}
