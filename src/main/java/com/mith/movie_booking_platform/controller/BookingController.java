package com.mith.movie_booking_platform.controller;

import com.mith.movie_booking_platform.request.BookingRequest;
import com.mith.movie_booking_platform.response.BookingResponse;
import com.mith.movie_booking_platform.service.BookingService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mithl
 * @date 20-02-2026
 * @email mithleshshah84@gmail.com
 */

@AllArgsConstructor
@RequestMapping("/api/booking")
@RestController
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingResponse> bookShow(@Valid @RequestBody BookingRequest bookingRequest){

        BookingResponse bookingResponse = bookingService.bookShow(bookingRequest);

        return ResponseEntity.status(201).body(bookingResponse);
    }
}
