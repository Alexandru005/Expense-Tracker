package com.alexstefan.expense_tracker.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService, CustomUserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        // 1. Extragem header-ul de autorizare din request
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // 2. Verificăm dacă header-ul lipsește sau nu începe cu "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response); // Lăsăm cererea să meargă mai departe (poate e login/register)
            return;
        }

        // 3. Extragem token-ul (sărim peste cuvântul "Bearer " care are 7 caractere)
        jwt = authHeader.substring(7);
        userEmail = jwtService.getEmail(jwt); // Folosim serviciul tău pentru a scoate email-ul

        // 4. Dacă am găsit un email și userul nu este deja autentificat
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Încărcăm detaliile userului din baza de date
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            // 5. Dacă token-ul este valid, îl autentificăm oficial în Spring Security
            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // Actualizăm contextul de securitate
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // 6. Trecem la următorul filtru
        filterChain.doFilter(request, response);
    }
}