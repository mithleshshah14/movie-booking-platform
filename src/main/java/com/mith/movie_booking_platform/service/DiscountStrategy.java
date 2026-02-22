package com.mith.movie_booking_platform.service;

import java.time.LocalTime;

/**
 * @author mithl
 * @date 22-02-2026
 * @email mithleshshah84@gmail.com
 */
public interface DiscountStrategy {
    double apply(double totalPrice, double seatPrice, int seatCount, LocalTime showTime);
}
