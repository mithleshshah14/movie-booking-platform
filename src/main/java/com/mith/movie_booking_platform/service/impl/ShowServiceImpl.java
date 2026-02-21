package com.mith.movie_booking_platform.service.impl;

import com.mith.movie_booking_platform.entity.Seat;
import com.mith.movie_booking_platform.entity.Show;
import com.mith.movie_booking_platform.enums.SeatStatus;
import com.mith.movie_booking_platform.exception.ResourceNotFoundException;
import com.mith.movie_booking_platform.mapper.SeatMapper;
import com.mith.movie_booking_platform.mapper.ShowMapper;
import com.mith.movie_booking_platform.repository.MovieRepository;
import com.mith.movie_booking_platform.repository.SeatRepository;
import com.mith.movie_booking_platform.repository.ShowRepository;
import com.mith.movie_booking_platform.response.SeatResponse;
import com.mith.movie_booking_platform.response.ShowResponse;
import com.mith.movie_booking_platform.service.ShowService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * @author mithl
 * @date 20-02-2026
 * @email mithleshshah84@gmail.com
 */
@AllArgsConstructor
@Service
public class ShowServiceImpl implements ShowService {

    private final ShowRepository showRepository;

    private final MovieRepository movieRepository;

    private final ShowMapper showMapper;

    private final SeatMapper seatMapper;

    private final SeatRepository seatRepository;

    @Override
    public List<ShowResponse> getShows(Long movieId, String city, LocalDate date) {

        movieRepository.findById(movieId).orElseThrow(() -> new ResourceNotFoundException("Movie not found with Id: "+movieId));

        List<Show> shows =   showRepository.getShows(movieId, city, date);

        return shows.stream().map(e -> showMapper.entityToResponse(e)).toList();
    }

    @Override
    public List<SeatResponse> getSeatsForShow(Long showId) {

        showRepository.findById(showId).orElseThrow(() -> new ResourceNotFoundException("Show not found for id: "+showId));

        List<Seat> seats = seatRepository.findByShowIdAndStatus(showId, SeatStatus.AVAILABLE);

        return seats.stream().map(s -> seatMapper.entityToResponse(s)).toList();
    }
}
