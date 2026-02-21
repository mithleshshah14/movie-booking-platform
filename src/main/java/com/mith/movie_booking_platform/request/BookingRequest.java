package com.mith.movie_booking_platform.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author mithl
 * @date 20-02-2026
 * @email mithleshshah84@gmail.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingRequest {

    @NotNull(message = "UserId is required")
    private String userId;

    @NotNull(message = "Show id is required")
    Long showId;

    @NotNull(message = "Seats are needed")
    List<String> seats;

}
