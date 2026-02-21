package com.mith.movie_booking_platform.repository;

import com.mith.movie_booking_platform.entity.Seat;
import com.mith.movie_booking_platform.enums.SeatStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author mithl
 * @date 20-02-2026
 * @email mithleshshah84@gmail.com
 */
@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

    List<Seat> findByShowIdAndStatus(Long showId, SeatStatus status);
}
