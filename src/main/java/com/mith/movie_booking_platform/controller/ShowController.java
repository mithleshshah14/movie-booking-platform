package com.mith.movie_booking_platform.controller;

import com.mith.movie_booking_platform.response.SeatResponse;
import com.mith.movie_booking_platform.response.ShowResponse;
import com.mith.movie_booking_platform.service.ShowService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * @author mithl
 * @date 20-02-2026
 * @email mithleshshah84@gmail.com
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/shows")
public class ShowController {

    private final ShowService showService;

    @GetMapping
    public ResponseEntity<List<ShowResponse>> getShows(@RequestParam Long movieId, @RequestParam String city,
                                                       @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate date){

    }

    @GetMapping("/{showId}/seats")
    public ResponseEntity<SeatResponse> getSeatsForShow(@PathVariable Long showId){

    }
}
