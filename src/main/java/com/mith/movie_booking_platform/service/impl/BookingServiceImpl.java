package com.mith.movie_booking_platform.service.impl;

import com.mith.movie_booking_platform.entity.Booking;
import com.mith.movie_booking_platform.entity.Seat;
import com.mith.movie_booking_platform.entity.Show;
import com.mith.movie_booking_platform.enums.BookingStatus;
import com.mith.movie_booking_platform.enums.SeatStatus;
import com.mith.movie_booking_platform.exception.ResourceNotFoundException;
import com.mith.movie_booking_platform.exception.SeatNotAvailableException;
import com.mith.movie_booking_platform.kafka.BookingEvent;
import com.mith.movie_booking_platform.kafka.BookingEventProducer;
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

    private final BookingEventProducer bookingEventProducer;

    @Transactional
    @Override
    public BookingResponse bookShow(BookingRequest bookingRequest) {

        Show show = showRepository.findById(bookingRequest.getShowId()).orElseThrow(() -> new ResourceNotFoundException("Show not found"));

        List<Seat> seatsToBook = seatRepository.findSeatByShowIdAndSeatNumberForBooking(bookingRequest.getShowId(), bookingRequest.getSeats());

        if (seatsToBook.size() != bookingRequest.getSeats().size()) {
            throw new SeatNotAvailableException("One or more seats not found");
        }

        List<Seat> bookedSeats = new ArrayList<>();
        for(Seat seat : seatsToBook){
          if(seat.getStatus() != SeatStatus.AVAILABLE) {
              throw new SeatNotAvailableException("Seat:"+seat.getSeatNumber()+" not available to book");
          }

          seat.setStatus(SeatStatus.BOOKED);
          bookedSeats.add(seat);
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

        Booking booked = bookingRepository.save(booking);

        BookingEvent bookingEvent = BookingEvent.builder()
                .bookingId(booked.getId())
                .movieTitle(show.getMovie().getTitle())
                .seatNumbers(booked.getSeats().stream().map(Seat::getSeatNumber).toList())
                .showDate(show.getShowDate())
                .theatreName(show.getTheatre().getName())
                .totalPrice(booked.getTotalPrice())
                .userId(booked.getUserId())
                .bookingTime(LocalDateTime.now())
                .showTime(show.getShowTime())
                .build();

        bookingEventProducer.publishBookingEvent(bookingEvent);

        return bookingMapper.entityToResponse(booked);
    }

    @Override
    public BookingResponse getBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new ResourceNotFoundException("Booking not found for id: "+bookingId));
        return bookingMapper.entityToResponse(booking);
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
