package com.mith.movie_booking_platform.repository;

import com.mith.movie_booking_platform.entity.Seat;
import com.mith.movie_booking_platform.enums.SeatStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select s from Seat s where s.show.id = :showId and s.seatNumber in :seatNumbers")
    List<Seat> findSeatByShowIdAndSeatNumberForBooking(@Param("showId") Long showId, @Param("seatNumbers") List<String> seatNumbers);

    int countByShowIdAndStatus(Long showId, SeatStatus status);
}
