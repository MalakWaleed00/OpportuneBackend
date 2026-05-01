package com.example.demo.common.config;

import com.example.demo.service.IJwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.hibernate.Hibernate;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTH_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";


    private final IJwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.startsWith("/api/auth")
                || path.startsWith("/swagger-ui")
                || path.startsWith("/v3/api-docs")
                || path.startsWith("/api/microsoft-auth")
                || path.startsWith("/login/oauth2")
                ;
    }


    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String path = request.getServletPath();

        // 🔥 Skip JWT logic for public endpoints
        if (path.startsWith("/api/auth")
                || path.startsWith("/swagger-ui")
                || path.startsWith("/v3/api-docs")
                || path.startsWith("/api/microsoft-auth")
                || path.startsWith("/login/oauth2")
                || path.startsWith("/api/jobs")
                || path.startsWith("/api/interview")
                || path.startsWith("/parse/upload")      ){

            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = extractJwtFromRequest(request);

        if (jwt != null) {
            final String username = extractUsernameFromJwt(jwt);

            if (username != null &&
                    SecurityContextHolder.getContext().getAuthentication() == null) {

                authenticateUser(username, jwt, request);
            }
        }

        filterChain.doFilter(request, response);
    }



    private String extractJwtFromRequest(HttpServletRequest request) {
        final String authHeader = request.getHeader(AUTH_HEADER);
        if (authHeader != null && authHeader.startsWith(TOKEN_PREFIX)) {
            return authHeader.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

    private String extractUsernameFromJwt(String jwt) {
        return jwtService.extractUsername(jwt);
    }

    private void authenticateUser(String username, String jwt, HttpServletRequest request) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // Force load of authorities (fixes LazyInitializationException)
        Hibernate.initialize(userDetails.getAuthorities());

        if (jwtService.isTokenValid(jwt, userDetails)) {
            var authToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
    }
}

