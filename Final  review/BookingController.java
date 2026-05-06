package com.example.ticket_booking_backend.controller;

import com.example.ticket_booking_backend.entity.Booking;
import com.example.ticket_booking_backend.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/{eventId}")
    public ResponseEntity<Booking> createBooking(@PathVariable Long eventId, @Valid @RequestBody Booking booking) {
        Booking createdBooking = bookingService.createBooking(booking, eventId);
        return new ResponseEntity<>(createdBooking, HttpStatus.CREATED);
    }
}
