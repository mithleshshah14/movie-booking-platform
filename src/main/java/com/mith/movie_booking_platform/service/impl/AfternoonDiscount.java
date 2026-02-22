package com.mith.movie_booking_platform.service.impl;

import com.mith.movie_booking_platform.service.DiscountStrategy;

import java.time.LocalTime;

/**
 * @author mithl
 * @date 22-02-2026
 * @email mithleshshah84@gmail.com
 */
public class AfternoonDiscount implements DiscountStrategy {
    @Override
    public double apply(double totalPrice, double seatPrice, int seatCount, LocalTime showTime) {
        if (showTime.isAfter(LocalTime.of(12, 0)) &&
                showTime.isBefore(LocalTime.of(17, 0))) {
            return totalPrice * 0.8;
        }
        return totalPrice;
    }
}
