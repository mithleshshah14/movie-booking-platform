package com.mith.movie_booking_platform.service.impl;

import com.mith.movie_booking_platform.service.DiscountStrategy;

import java.time.LocalTime;

/**
 * @author mithl
 * @date 22-02-2026
 * @email mithleshshah84@gmail.com
 */
public class ThirdTicketDiscount implements DiscountStrategy {
    @Override
    public double apply(double totalPrice, double seatPrice, int seatCount, LocalTime showTime) {
        if (seatCount >= 3) {
            return totalPrice - (seatPrice * 0.5);
        }
        return totalPrice;
    }
}
