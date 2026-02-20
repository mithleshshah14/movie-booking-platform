package com.mith.movie_booking_platform.repository;

import com.mith.movie_booking_platform.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author mithl
 * @date 20-02-2026
 * @email mithleshshah84@gmail.com
 */
@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
}
