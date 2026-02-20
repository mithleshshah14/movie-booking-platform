package com.mith.movie_booking_platform.repository;

import com.mith.movie_booking_platform.entity.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * @author mithl
 * @date 20-02-2026
 * @email mithleshshah84@gmail.com
 */
@Repository
public interface ShowRepository extends JpaRepository<Show, Long> {

    List<Show> getShows(Long MoviesId, String city, LocalDate date);
}
