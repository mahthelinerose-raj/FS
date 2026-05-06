package com.example.ticket_booking_backend.service;

import com.example.ticket_booking_backend.entity.Booking;
import com.example.ticket_booking_backend.entity.Event;
import com.example.ticket_booking_backend.repository.BookingRepository;
import com.example.ticket_booking_backend.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final EventService eventService;
    private final EventRepository eventRepository;

    @Transactional
    public Booking createBooking(Booking bookingRequest, Long eventId) {
        Event event = eventService.getEventById(eventId);

        if (event.getAvailableTickets() < bookingRequest.getTicketsBooked()) {
            throw new IllegalArgumentException("Not enough tickets available. Only " + 
                    event.getAvailableTickets() + " left.");
        }

        // Deduct tickets
        event.setAvailableTickets(event.getAvailableTickets() - bookingRequest.getTicketsBooked());
        eventRepository.save(event);

        // Calculate total amount
        BigDecimal totalAmount = event.getPrice().multiply(new BigDecimal(bookingRequest.getTicketsBooked()));
        
        // Save booking
        bookingRequest.setEvent(event);
        bookingRequest.setTotalAmount(totalAmount);
        return bookingRepository.save(bookingRequest);
    }
}
