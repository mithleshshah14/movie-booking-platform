# Movie Booking Platform — Solution Document
**Candidate:** Mithlesh Shah | Senior Backend Engineer
**Role:** Senior Associate Technology L2 — Publicis Sapient

---

## 1. Problem Statement

XYZ wants to build an online movie ticket booking platform catering to:
- **B2B** — Theatre partners to onboard, manage shows and seat inventory
- **B2C** — End customers to browse movies, book tickets seamlessly

---

## 2. Functional Features Implemented

### Read Scenarios
- Browse shows by movie, city, and date → `GET /api/shows`
- Get available seats for a show → `GET /api/shows/{showId}/seats`
- Get all shows → `GET /api/shows/all`

### Write Scenarios
- Book movie tickets → `POST /api/booking`
- Get booking details → `GET /api/booking/{bookingId}`

### Discount/Offers Implemented
- **50% off on 3rd ticket** — `ThirdTicketDiscountStrategy`
- **20% off on afternoon shows** (12pm–5pm) — `AfternoonDiscountStrategy`
- Both implemented using **Strategy Pattern** — new discounts can be added without modifying existing code (Open/Closed Principle)

---

## 3. Design Patterns Used

| Pattern | Where Used | Why |
|---|---|---|
| **Strategy** | Discount logic | Each discount rule is interchangeable, new ones added without code change |
| **Builder** | Booking, BookingEvent entities | Complex objects with many fields |
| **Observer** | Kafka — BookingEventProducer + NotificationService | Booking confirmed → Email + SMS react independently |
| **Factory** | Extendable for notification types | Create Email/SMS/Push without exposing logic |

---

## 4. High Level Architecture

```
Client (Web/Mobile)
        ↓
   API Gateway (Rate Limiting, Auth, Routing)
        ↓
┌───────────┬───────────┬───────────┬───────────┐
│  Movie    │  Show     │  Booking  │  Theatre  │
│  Service  │  Service  │  Service  │  Service  │
└───────────┴───────────┴───────────┴───────────┘
        ↓                    ↓
   PostgreSQL            PostgreSQL
   (Movies/Shows)        (Bookings/Seats)
                             ↓
                           Kafka
                     (booking-events topic)
                             ↓
                   NotificationService (Consumer)
                        ↓         ↓
                     Email        SMS
```

---

## 5. Database Design

### Key Decisions
- **PostgreSQL** — relational data, ACID transactions critical for seat booking
- **Pessimistic Write Lock** on seat fetch — prevents double booking at DB level
- **`@Transactional`** on bookShow — entire booking is atomic

### Tables
```
movies        (id, title, description, duration, language, genre)
theatres      (id, name, city, address)
screens       (id, theatre_id, name, total_seats)
shows         (id, movie_id, theatre_id, screen_id, show_date, show_time, price)
seats         (id, show_id, seat_number, status)
bookings      (id, show_id, user_id, status, total_price, booking_time)
booking_seats (booking_id, seat_id)
```

### Indexes
```sql
CREATE INDEX idx_shows_movie_date  ON shows(movie_id, show_date);
CREATE INDEX idx_theatres_city     ON theatres(city);
CREATE INDEX idx_seats_show_status ON seats(show_id, status);
CREATE INDEX idx_bookings_user     ON bookings(user_id);
```

---

## 6. Non-Functional Requirements

### 6.1 Concurrency & Transactions
- **Problem:** Two users booking same seat simultaneously → double booking
- **Solution:** `@Lock(PESSIMISTIC_WRITE)` on seat fetch query
- **Flow:** Seat locked at DB level → only one transaction proceeds → other fails with clear error
- **`@Transactional`** ensures atomicity — if anything fails, entire booking rolls back

### 6.2 Scalability — 99.99% Availability
- **Horizontal scaling** — multiple instances behind load balancer (AWS ALB)
- **Read replicas** — show browsing hits read replica, bookings hit master
- **Redis caching** — movie/show data cached, reduces DB load on high traffic
- **CDN** — movie posters, static content via CloudFront
- **Kafka** — async notifications, booking service not blocked by email/SMS latency
- **Circuit Breaker** — Resilience4j to handle downstream failures gracefully

### 6.3 Payment Gateway Integration
- After seat lock confirmed → redirect to payment gateway (Razorpay/Stripe)
- On payment **success** → Booking status = CONFIRMED, publish Kafka event
- On payment **failure** → Release seat lock, Booking status = FAILED
- **Idempotency key** on payment API — prevents duplicate charges on retry
- Payment timeout handled via scheduled job to release expired locks

### 6.4 Legacy Theatre IT Integration — Adapter Pattern
- Existing theatres may have their own IT systems with different data formats
- **Adapter Pattern** — converts legacy theatre data to platform format without modifying either system
- New theatres onboard via standard REST API
- CSV bulk upload supported for show/seat inventory

### 6.5 Security — OWASP Top 10

| Threat | Solution |
|---|---|
| Broken Authentication | JWT tokens with expiry + refresh tokens |
| SQL Injection | JPA parameterized queries — no raw SQL |
| Broken Access Control | Role-based (ADMIN, THEATRE_PARTNER, USER) |
| Rate Limiting | API Gateway — max requests per IP/minute |
| Sensitive Data Exposure | HTTPS only, no PII in logs, BCrypt for passwords |
| CSRF | Stateless JWT — no session cookies |

### 6.6 Monitoring & Logging
- **Spring Boot Actuator** — health checks, metrics endpoints
- **Structured logging** with SLF4J + correlation IDs per request
- **Kafka consumer lag** monitoring — alert if notification processing falls behind
- **Alerts** — booking failure rate > 1%, seat lock timeout spikes

---

## 7. Tech Choices & Reasoning

| Technology | Choice | Why |
|---|---|---|
| Language | Java 17 + Spring Boot | Production-proven, team expertise |
| Database | PostgreSQL | ACID compliance critical for financial transactions |
| Messaging | Kafka | High throughput, replay capability, multiple consumers |
| Locking | Pessimistic Write Lock | Simpler, consistent, no distributed lock complexity |
| Notifications | Async via Kafka | Booking response not blocked by email/SMS latency |
| Cloud | AWS | EKS for containers, RDS for PostgreSQL, MSK for Kafka |
| Reactive | Spring WebFlux (Mono/Flux) | Non-blocking I/O for read-heavy show browsing — show endpoints use Mono/Flux, booking stays traditional due to pessimistic locking + @Transactional |

---

## 8. How to Monetize

- **Commission per booking** — % of ticket price per transaction
- **Premium listing** — theatres pay for featured placement
- **Advertising** — movie promotions on platform
- **Convenience fee** — charged to end customer per booking

---

## 9. Scalability to Multiple Cities/Countries

- **City-based data partitioning** — shows/theatres partitioned by city
- **Multi-region AWS deployment** — separate regions per geography
- **Localization** — language, currency, date format per region
- **CDN** — regional movie content served closer to users

---

## 10. What Can Be Extended

| Feature | Status |
|---|---|
| Theatre CRUD endpoints | Stubbed — TheatreServiceImpl |
| Bulk booking and cancellation | Not implemented |
| Seat inventory management by theatre partners | Not implemented |
| Cancellation and refund flow | Not implemented |
| Waitlist for sold-out shows | Not implemented |
| Payment gateway integration | Designed, not implemented |

---

## 11. Project Estimate (High Level)

| Phase | Effort |
|---|---|
| Core booking flow (done) | 1 week |
| Theatre partner onboarding | 1 week |
| Payment gateway integration | 3 days |
| Security & Auth | 3 days |
| Monitoring & DevOps | 2 days |
| Testing | 1 week |
| **Total** | **~4 weeks** |