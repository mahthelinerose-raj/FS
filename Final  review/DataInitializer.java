package com.example.ticket_booking_backend.config;

import com.example.ticket_booking_backend.entity.Event;
import com.example.ticket_booking_backend.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final EventRepository eventRepository;

    @Override
    public void run(String... args) throws Exception {
        if (eventRepository.count() == 0) {
            eventRepository.saveAll(Arrays.asList(
                    Event.builder()
                            .name("Annual Tech Fest 2026")
                            .department("Computer Science")
                            .dateTime(LocalDateTime.now().plusDays(10).withHour(10).withMinute(0))
                            .venue("Main Auditorium")
                            .price(new BigDecimal("150.00"))
                            .availableTickets(500)
                            .build(),
                    Event.builder()
                            .name("AI & Machine Learning Workshop")
                            .department("Information Technology")
                            .dateTime(LocalDateTime.now().plusDays(5).withHour(14).withMinute(30))
                            .venue("Lab 4B")
                            .price(new BigDecimal("50.00"))
                            .availableTickets(50)
                            .build(),
                    Event.builder()
                            .name("Cybersecurity Seminar")
                            .department("Computer Science")
                            .dateTime(LocalDateTime.now().plusDays(15).withHour(11).withMinute(0))
                            .venue("Seminar Hall A")
                            .price(new BigDecimal("0.00")) // Free event
                            .availableTickets(100)
                            .build()
            ));
            System.out.println("Sample events initialized.");
        }
    }
}
