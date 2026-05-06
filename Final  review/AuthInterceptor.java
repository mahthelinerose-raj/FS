package com.example.ticket_booking_backend.config;

import com.example.ticket_booking_backend.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Value("${app.admin.token}")
    private String adminToken;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Allow CORS Preflight and GET requests
        if (HttpMethod.OPTIONS.name().equals(request.getMethod()) || HttpMethod.GET.name().equals(request.getMethod())) {
            return true;
        }

        String path = request.getRequestURI();
        String authHeader = request.getHeader("Authorization");
        String token = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        }

        if (token == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized: Missing token");
            return false;
        }

        // Admin paths
        if (path.startsWith("/api/events")) {
            if (adminToken.equals(token)) {
                return true;
            }
        } 
        // User paths
        else if (path.startsWith("/api/bookings")) {
            if (userService.findByToken(token).isPresent()) {
                // Optionally store user ID in request attribute
                request.setAttribute("userId", userService.findByToken(token).get().getId());
                return true;
            }
        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("Unauthorized: Invalid token or insufficient permissions");
        return false;
    }
}
