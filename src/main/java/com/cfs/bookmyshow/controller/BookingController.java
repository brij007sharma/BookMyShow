package com.cfs.bookmyshow.controller;

import com.cfs.bookmyshow.dto.BookingRequest;
import com.cfs.bookmyshow.entity.Booking;
import com.cfs.bookmyshow.entity.Seat;
import com.cfs.bookmyshow.service.BookingService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;


    // ==========================================
    // CREATE BOOKING
    // ==========================================

    @PostMapping
    public ResponseEntity<Booking> createBooking(
            @RequestBody BookingRequest request
    ) {

        return ResponseEntity.ok(
                bookingService.createBooking(request)
        );
    }


    // ==========================================
    // GET MY BOOKINGS
    // ==========================================

    @GetMapping("/my")
    public ResponseEntity<List<Booking>> getMyBookings() {

        return ResponseEntity.ok(
                bookingService.getMyBookings()
        );
    }


    // ==========================================
    // GET BOOKING BY ID
    // ==========================================

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                bookingService.getBookingById(id)
        );
    }


    // ==========================================
    // CANCEL BOOKING
    // ==========================================

    @PutMapping("/{id}/cancel")
    public ResponseEntity<Booking> cancelBooking(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                bookingService.cancelBooking(id)
        );
    }


    // ==========================================
    // AVAILABLE SEATS
    // ==========================================

    @GetMapping("/show/{showId}/available-seats")
    public ResponseEntity<List<Seat>> getAvailableSeats(
            @PathVariable Long showId
    ) {

        return ResponseEntity.ok(
                bookingService.getAvailableSeats(showId)
        );
    }
}