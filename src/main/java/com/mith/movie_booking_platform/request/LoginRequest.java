package com.mith.movie_booking_platform.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mithl
 * @date 23-02-2026
 * @email mithleshshah84@gmail.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    private String username;
    private String password;
}
