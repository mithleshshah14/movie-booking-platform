package com.mith.movie_booking_platform.response;

import com.mith.movie_booking_platform.enums.SeatStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mithl
 * @date 20-02-2026
 * @email mithleshshah84@gmail.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatResponse {

    private Long id;

    private String seatNumber;

    private SeatStatus status;

    private Long version;
}
