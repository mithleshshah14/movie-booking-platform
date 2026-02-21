-- Movies
INSERT INTO movies (id, title, language, genre, duration_minutes) VALUES
                                                                      (1, 'Avengers: Endgame', 'English', 'Action', 181),
                                                                      (2, 'Jawan', 'Hindi', 'Action', 169),
                                                                      (3, 'Oppenheimer', 'English', 'Drama', 180);

-- Theatres
INSERT INTO theatres (id, name, city, address) VALUES
                                                   (1, 'PVR Cinemas', 'Bhopal', 'DB Mall, Zone 2'),
                                                   (2, 'INOX', 'Bhopal', 'Aashima Mall, Hoshangabad Road'),
                                                   (3, 'Cinepolis', 'Bhopal', 'DB City Mall');

-- Shows
INSERT INTO shows (id, movie_id, theatre_id, show_date, show_time, price, total_seats) VALUES
                                                                                           (1, 1, 1, '2025-02-26', '10:00:00', 200.0, 50),
                                                                                           (2, 1, 1, '2025-02-26', '14:00:00', 200.0, 50),
                                                                                           (3, 1, 2, '2025-02-26', '11:00:00', 250.0, 40),
                                                                                           (4, 2, 1, '2025-02-26', '15:00:00', 180.0, 60),
                                                                                           (5, 3, 3, '2025-02-26', '18:00:00', 300.0, 30);

-- Seats for Show 1 (PVR - 10:00 AM)
INSERT INTO seats (id, show_id, seat_number, status, version) VALUES
                                                                  (1, 1, 'A1', 'AVAILABLE', 0),
                                                                  (2, 1, 'A2', 'AVAILABLE', 0),
                                                                  (3, 1, 'A3', 'AVAILABLE', 0),
                                                                  (4, 1, 'A4', 'AVAILABLE', 0),
                                                                  (5, 1, 'B1', 'AVAILABLE', 0),
                                                                  (6, 1, 'B2', 'AVAILABLE', 0),
                                                                  (7, 1, 'B3', 'AVAILABLE', 0),
                                                                  (8, 1, 'B4', 'AVAILABLE', 0);

-- Seats for Show 2 (PVR - 2:00 PM - afternoon show for discount testing)
INSERT INTO seats (id, show_id, seat_number, status, version) VALUES
                                                                  (9, 2, 'A1', 'AVAILABLE', 0),
                                                                  (10, 2, 'A2', 'AVAILABLE', 0),
                                                                  (11, 2, 'A3', 'AVAILABLE', 0),
                                                                  (12, 2, 'A4', 'AVAILABLE', 0),
                                                                  (13, 2, 'B1', 'AVAILABLE', 0),
                                                                  (14, 2, 'B2', 'AVAILABLE', 0);

-- Seats for Show 3 (INOX - 11:00 AM)
INSERT INTO seats (id, show_id, seat_number, status, version) VALUES
                                                                  (15, 3, 'A1', 'AVAILABLE', 0),
                                                                  (16, 3, 'A2', 'AVAILABLE', 0),
                                                                  (17, 3, 'A3', 'BOOKED', 0),
                                                                  (18, 3, 'B1', 'AVAILABLE', 0);