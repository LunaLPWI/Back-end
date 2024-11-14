package com.luna.luna_project.configurations;

import com.luna.luna_project.configurations.jwt.GerenciadorTokenJwt;
import com.luna.luna_project.services.AuthenticationService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class AuthenticationFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationFilter.class);
    private final AuthenticationService authenticationService;
    private final GerenciadorTokenJwt jwtTokenManager;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public AuthenticationFilter(AuthenticationService authenticationService, GerenciadorTokenJwt jwtTokenManager) {
        this.authenticationService = authenticationService;
        this.jwtTokenManager = jwtTokenManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String requestTokenHeader = request.getHeader("Authorization");

            // Skip authentication for OPTIONS requests and permitted URLs
            if (request.getMethod().equals("OPTIONS") || isPermittedUrl(request.getRequestURI())) {
                filterChain.doFilter(request, response);
                return;
            }

            // Process token if present
            if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
                String jwtToken = requestTokenHeader.substring(7);
                try {
                    String username = jwtTokenManager.getUsernameFromToken(jwtToken);
                    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                        processAuthentication(request, jwtToken, username);
                    }
                } catch (ExpiredJwtException e) {
                    handleAuthenticationError(response, HttpServletResponse.SC_UNAUTHORIZED,
                            "Token expirado", e.getMessage());
                    return;
                } catch (Exception e) {
                    handleAuthenticationError(response, HttpServletResponse.SC_UNAUTHORIZED,
                            "Erro no token", e.getMessage());
                    return;
                }
            }

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            handleAuthenticationError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro interno", e.getMessage());
        }
    }

    private void processAuthentication(HttpServletRequest request, String jwtToken, String username) {
        UserDetails userDetails = authenticationService.loadUserByUsername(username);
        if (jwtTokenManager.validateToken(jwtToken, userDetails)) {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }

    private void handleAuthenticationError(HttpServletResponse response, int status, String error, String message)
            throws IOException {
        LOGGER.error("Authentication error: {} - {}", error, message);

        response.setStatus(status);
        response.setContentType("application/json;charset=UTF-8");

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now().toString());
        errorResponse.put("status", status);
        errorResponse.put("error", error);
        errorResponse.put("message", message);

        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }

    private boolean isPermittedUrl(String requestURI) {
        // Add your permitted URLs logic here
        return requestURI.contains("/public/") ||
                requestURI.contains("/clients/login") ||
                requestURI.contains("/swagger-ui/") ||
                requestURI.contains("/v3/api-docs");
    }
}