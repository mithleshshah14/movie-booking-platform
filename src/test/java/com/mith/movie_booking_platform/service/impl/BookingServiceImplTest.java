package com.mith.movie_booking_platform.service.impl;

import com.mith.movie_booking_platform.kafka.BookingEventProducer;
import com.mith.movie_booking_platform.mapper.BookingMapper;
import com.mith.movie_booking_platform.repository.BookingRepository;
import com.mith.movie_booking_platform.repository.SeatRepository;
import com.mith.movie_booking_platform.repository.ShowRepository;
import com.mith.movie_booking_platform.service.DiscountStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author mithl
 * @date 23-02-2026
 * @email mithleshshah84@gmail.com
 */
@ExtendWith(MockitoExtension.class)
class BookingServiceImplTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private ShowRepository showRepository;

    @Mock
    private SeatRepository seatRepository;

    @Mock
    private BookingMapper bookingMapper;

    @Mock
    private BookingEventProducer bookingEventProducer;

    private BookingServiceImpl bookingService;

    @BeforeEach
    void setUp() {
        List<DiscountStrategy> strategies = List.of(
                new ThirdTicketDiscount(),
                new AfternoonDiscount()
        );

        bookingService = new BookingServiceImpl(
                bookingRepository,
                showRepository,
                seatRepository,
                bookingMapper,
                bookingEventProducer,
                strategies
        );
    }

    @Test
    void shouldApply50PercentDiscountOnThirdTicket() {
        double seatPrice = 100.0;
        int seatCount = 3;
        LocalTime showTime = LocalTime.of(19, 0);

        double result = bookingService.calculatePrice(seatPrice, seatCount, showTime);

        assertEquals(250.0, result);
    }
}