package com.mith.movie_booking_platform.controller;

import com.mith.movie_booking_platform.response.SeatResponse;
import com.mith.movie_booking_platform.response.ShowResponse;
import com.mith.movie_booking_platform.service.ShowService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
    public Mono<ResponseEntity<List<ShowResponse>>> getShows(
            @RequestParam Long movieId,
            @RequestParam String city,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        return Mono.fromCallable(() -> showService.getShows(movieId, city, date))
                .map(ResponseEntity::ok);
    }

    @GetMapping("/all")
    public Flux<ShowResponse> getAllShows() {
        return Flux.fromIterable(showService.getAllShows());
    }


        @GetMapping("/{showId}/seats")
    public ResponseEntity<List<SeatResponse>> getSeatsForShow(@PathVariable Long showId){
        List<SeatResponse> seatResponseList = showService.getSeatsForShow(showId);
        return ResponseEntity.ok(seatResponseList);
    }
}
