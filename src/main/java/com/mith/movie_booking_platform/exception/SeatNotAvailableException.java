package com.mith.movie_booking_platform.exception;

/**
 * @author mithl
 * @date 21-02-2026
 * @email mithleshshah84@gmail.com
 */
public class SeatNotAvailableException extends RuntimeException{

    public SeatNotAvailableException(String message){
        super(message);
    }
}
