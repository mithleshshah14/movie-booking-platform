# API Contract — Movie Booking Platform

**Base URL:** `https://api.moviebooking.com/api`
**Auth:** Bearer JWT Token in Authorization header
**Content-Type:** `application/json`

---

## 1. Show Endpoints

### GET /shows
Browse shows by movie, city and date.

**Query Parameters:**
| Param | Type | Required | Description |
|---|---|---|---|
| movieId | Long | Yes | Movie ID |
| city | String | Yes | City name |
| date | LocalDate | Yes | Show date (yyyy-MM-dd) |

**Request:**
```
GET /api/shows?movieId=1&city=Mumbai&date=2026-02-22
Authorization: Bearer <token>
```

**Response 200:**
```json
[
  {
    "showId": 1,
    "movieTitle": "Inception",
    "theatreName": "PVR Mumbai",
    "city": "Mumbai",
    "showDate": "2026-02-22",
    "showTime": "14:00",
    "price": 250.0,
    "availableSeats": 45
  }
]
```

**Error Responses:**
| Code | Reason |
|---|---|
| 404 | Movie not found |
| 400 | Invalid date format |

---

### GET /shows/all
Get all shows in the system.

**Request:**
```
GET /api/shows/all
Authorization: Bearer <token>
```

**Response 200:**
```json
[
  {
    "showId": 1,
    "movieTitle": "Inception",
    "theatreName": "PVR Mumbai",
    "city": "Mumbai",
    "showDate": "2026-02-22",
    "showTime": "14:00",
    "price": 250.0
  }
]
```

---

### GET /shows/{showId}/seats
Get available seats for a specific show.

**Path Parameters:**
| Param | Type | Required | Description |
|---|---|---|---|
| showId | Long | Yes | Show ID |

**Request:**
```
GET /api/shows/1/seats
Authorization: Bearer <token>
```

**Response 200:**
```json
[
  { "seatId": 1, "seatNumber": "A1", "status": "AVAILABLE" },
  { "seatId": 2, "seatNumber": "A2", "status": "AVAILABLE" },
  { "seatId": 3, "seatNumber": "A3", "status": "BOOKED" }
]
```

**Error Responses:**
| Code | Reason |
|---|---|
| 404 | Show not found |

---

## 2. Booking Endpoints

### POST /booking
Book movie tickets for a show.

**Request:**
```
POST /api/booking
Authorization: Bearer <token>
Content-Type: application/json
```
```json
{
  "userId": "user123",
  "showId": 1,
  "seats": ["A1", "A2", "A3"]
}
```

**Validation:**
| Field | Rule |
|---|---|
| userId | Not null |
| showId | Not null |
| seats | Not null |

**Response 201:**
```json
{
  "bookingId": 101,
  "userId": "user123",
  "movieTitle": "Inception",
  "theatreName": "PVR Mumbai",
  "showDate": "2026-02-22",
  "showTime": "14:00",
  "seats": ["A1", "A2", "A3"],
  "totalPrice": 598.50,
  "status": "CONFIRMED",
  "bookingTime": "2026-02-22T10:30:00"
}
```

**Discount Logic Applied:**
- 3 seats booked → 50% off on 3rd ticket (ThirdTicketDiscountStrategy)
- Show between 12pm–5pm → additional 20% off (AfternoonDiscountStrategy)

**Error Responses:**
| Code | Reason |
|---|---|
| 404 | Show not found |
| 409 | Seat not available / already booked |
| 400 | Validation failed |

---

### GET /booking/{bookingId}
Get booking details by ID.

**Path Parameters:**
| Param | Type | Required | Description |
|---|---|---|---|
| bookingId | Long | Yes | Booking ID |

**Request:**
```
GET /api/booking/101
Authorization: Bearer <token>
```

**Response 200:**
```json
{
  "bookingId": 101,
  "userId": "user123",
  "movieTitle": "Inception",
  "theatreName": "PVR Mumbai",
  "showDate": "2026-02-22",
  "showTime": "14:00",
  "seats": ["A1", "A2", "A3"],
  "totalPrice": 598.50,
  "status": "CONFIRMED",
  "bookingTime": "2026-02-22T10:30:00"
}
```

**Error Responses:**
| Code | Reason |
|---|---|
| 404 | Booking not found |

---

## 3. Booking Status Enum
| Status | Description |
|---|---|
| CONFIRMED | Booking successful |
| FAILED | Payment failed or seats unavailable |
| CANCELLED | Cancelled by user |

---

## 4. Kafka Events

### Topic: `booking-events`
Published after every successful booking.

```json
{
  "bookingId": 101,
  "userId": "user123",
  "movieTitle": "Inception",
  "theatreName": "PVR Mumbai",
  "showDate": "2026-02-22",
  "showTime": "14:00",
  "seatNumbers": ["A1", "A2", "A3"],
  "totalPrice": 598.50,
  "bookingTime": "2026-02-22T10:30:00"
}
```

**Consumers:**
- `notification-group` → Sends Email + SMS to user