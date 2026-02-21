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

    @Query("select s from Show s join s.theatre t join s.movie m where m.id= :movieId and s.showDate= :date and t.city = :city")
    List<Show> getShows (Long movieId, String city, LocalDate date);
}
