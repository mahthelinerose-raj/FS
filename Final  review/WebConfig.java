package com.example.ticket_booking_backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Protect POST /api/events with AuthInterceptor (Admin)
        // Protect POST /api/bookings with AuthInterceptor (User)
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/api/events/**", "/api/bookings/**");
    }
}
