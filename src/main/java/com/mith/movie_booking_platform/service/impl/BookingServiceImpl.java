package com.mith.movie_booking_platform.service.impl;

import com.mith.movie_booking_platform.entity.Booking;
import com.mith.movie_booking_platform.entity.Seat;
import com.mith.movie_booking_platform.entity.Show;
import com.mith.movie_booking_platform.enums.BookingStatus;
import com.mith.movie_booking_platform.enums.SeatStatus;
import com.mith.movie_booking_platform.exception.ResourceNotFoundException;
import com.mith.movie_booking_platform.exception.SeatNotAvailableException;
import com.mith.movie_booking_platform.mapper.BookingMapper;
import com.mith.movie_booking_platform.repository.BookingRepository;
import com.mith.movie_booking_platform.repository.SeatRepository;
import com.mith.movie_booking_platform.repository.ShowRepository;
import com.mith.movie_booking_platform.request.BookingRequest;
import com.mith.movie_booking_platform.response.BookingResponse;
import com.mith.movie_booking_platform.service.BookingService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mithl
 * @date 20-02-2026
 * @email mithleshshah84@gmail.com
 */
@AllArgsConstructor
@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    private final ShowRepository showRepository;

    private final SeatRepository seatRepository;

    private final BookingMapper bookingMapper;

    @Transactional
    @Override
    public BookingResponse bookShow(BookingRequest bookingRequest) {

        Show show = showRepository.findById(bookingRequest.getShowId()).orElseThrow(() -> new ResourceNotFoundException("Show not found"));

        List<Seat> availableSeats = seatRepository.findByShowIdAndStatus(show.getId(), SeatStatus.AVAILABLE);

        List<String> seatsToBook =bookingRequest.getSeats();

        List<Seat> bookedSeats = new ArrayList<>();
        for(String seatNumber : seatsToBook){
            for(Seat seat : availableSeats){
                if(seat.getSeatNumber().equalsIgnoreCase(seatNumber)){
                    seat.setStatus(SeatStatus.BOOKED);
                    bookedSeats.add(seat);
                }
            }
        }

        if(bookedSeats.isEmpty()){
            throw new SeatNotAvailableException("Chosen seats not available for booking!");
        }

        int seatCount = seatsToBook.size();
        double showPrice = calculatePrice(show.getPrice(), seatCount, show.getShowTime());


        seatRepository.saveAll(bookedSeats);

        Booking booking = Booking.builder()
                .bookingTime(LocalDateTime.now())
                .show(show)
                .seats(bookedSeats)
                .userId(bookingRequest.getUserId())
                .status(BookingStatus.CONFIRMED)
                .totalPrice(seatCount * showPrice)
                .build();

        return bookingMapper.entityToResponse(bookingRepository.save(booking));
    }

    public static double calculatePrice(double price, int numberOfSeats, LocalTime showTime) {
        double totalPrice = price * numberOfSeats;

        if(numberOfSeats >= 3){
            totalPrice -= (price * 0.5);
        }

        if (showTime.isAfter(LocalTime.of(12, 0)) &&
                showTime.isBefore(LocalTime.of(17, 0))) {
            totalPrice *= 0.8;
        }

        return totalPrice;
    }
}
