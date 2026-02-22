-- =============================================
-- Movie Booking Platform - Database Schema
-- Database: PostgreSQL
-- =============================================

-- Movies
CREATE TABLE movies (
                        id          BIGSERIAL PRIMARY KEY,
                        title       VARCHAR(255) NOT NULL,
                        description TEXT,
                        duration    INT,                        -- in minutes
                        language    VARCHAR(50),
                        genre       VARCHAR(50),
                        created_at  TIMESTAMP DEFAULT NOW()
);

-- Theatres
CREATE TABLE theatres (
                          id          BIGSERIAL PRIMARY KEY,
                          name        VARCHAR(255) NOT NULL,
                          city        VARCHAR(100) NOT NULL,
                          address     TEXT,
                          created_at  TIMESTAMP DEFAULT NOW()
);

-- Screens
CREATE TABLE screens (
                         id          BIGSERIAL PRIMARY KEY,
                         theatre_id  BIGINT NOT NULL REFERENCES theatres(id),
                         name        VARCHAR(100),
                         total_seats INT NOT NULL
);

-- Shows
CREATE TABLE shows (
                       id          BIGSERIAL PRIMARY KEY,
                       movie_id    BIGINT NOT NULL REFERENCES movies(id),
                       theatre_id  BIGINT NOT NULL REFERENCES theatres(id),
                       screen_id   BIGINT NOT NULL REFERENCES screens(id),
                       show_date   DATE NOT NULL,
                       show_time   TIME NOT NULL,
                       price       DECIMAL(10, 2) NOT NULL
);

-- Seats
CREATE TABLE seats (
                       id          BIGSERIAL PRIMARY KEY,
                       show_id     BIGINT NOT NULL REFERENCES shows(id),
                       seat_number VARCHAR(10) NOT NULL,
                       status      VARCHAR(20) DEFAULT 'AVAILABLE'   -- AVAILABLE, BOOKED
);

-- Bookings
CREATE TABLE bookings (
                          id           BIGSERIAL PRIMARY KEY,
                          show_id      BIGINT NOT NULL REFERENCES shows(id),
                          user_id      VARCHAR(100) NOT NULL,
                          status       VARCHAR(20) NOT NULL,              -- CONFIRMED, FAILED, CANCELLED
                          total_price  DECIMAL(10, 2) NOT NULL,
                          booking_time TIMESTAMP DEFAULT NOW()
);

-- Booking Seats (join table)
CREATE TABLE booking_seats (
                               booking_id  BIGINT NOT NULL REFERENCES bookings(id),
                               seat_id     BIGINT NOT NULL REFERENCES seats(id),
                               PRIMARY KEY (booking_id, seat_id)
);

-- =============================================
-- Indexes
-- =============================================

-- Most common query: find shows by movie, city, date
CREATE INDEX idx_shows_movie_date ON shows(movie_id, show_date);
CREATE INDEX idx_theatres_city    ON theatres(city);

-- Seat availability check for a show
CREATE INDEX idx_seats_show_status ON seats(show_id, status);

-- Booking lookup by user
CREATE INDEX idx_bookings_user ON bookings(user_id);

-- =============================================
-- Sample Data
-- =============================================

INSERT INTO movies (title, description, duration, language, genre)
VALUES ('Inception', 'A thief who steals corporate secrets', 148, 'English', 'Sci-Fi'),
       ('The Dark Knight', 'Batman faces the Joker', 152, 'English', 'Action');

INSERT INTO theatres (name, city, address)
VALUES ('PVR Mumbai', 'Mumbai', 'Juhu, Mumbai'),
       ('INOX Delhi', 'Delhi', 'Connaught Place, Delhi');

INSERT INTO shows (movie_id, theatre_id, screen_id, show_date, show_time, price)
VALUES (1, 1, 1, '2026-02-22', '14:00', 250.00),   -- afternoon show (20% discount)
       (1, 1, 1, '2026-02-22', '19:00', 300.00);    -- evening show (no discount)
