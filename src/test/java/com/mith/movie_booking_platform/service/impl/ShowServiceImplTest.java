package com.mith.movie_booking_platform.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * @author mithl
 * @date 21-02-2026
 * @email mithleshshah84@gmail.com
 */
@AutoConfigureMockMvc
@SpringBootTest
class ShowServiceImplTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getShows() throws Exception {

        mockMvc.perform(get("/api/shows").param("movieId","1")
                .param("city","Bhopal").param("date","2025-02-26"))
                .andExpect(jsonPath("$",hasSize(3)));

    }

    @Test
    void getAllShows() throws Exception {

        mockMvc.perform(get("/api/shows/all"))
                .andExpect(jsonPath("$",hasSize(5)));
    }
}