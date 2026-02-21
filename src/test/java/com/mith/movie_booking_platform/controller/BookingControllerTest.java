package com.mith.movie_booking_platform.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author mithl
 * @date 21-02-2026
 * @email mithleshshah84@gmail.com
 */
@AutoConfigureMockMvc
@SpringBootTest
class BookingControllerTest {

    private MockMvc mockMvc;

    @Test
    void bookShow() throws Exception {
        String body="""
                {
                  "showId": 1,
                  "userId": "user123",
                  "seats": ["A1", "A2", "A3"]
                }""";

        mockMvc.perform(post("/api/booking").contentType(MediaType.APPLICATION_JSON)
                .content(body)).andExpect(status().isCreated()).andExpect(jsonPath("$.seatNumbers",hasSize(3)));
    }
}