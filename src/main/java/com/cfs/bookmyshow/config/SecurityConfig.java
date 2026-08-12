package com.cfs.bookmyshow.config;

import com.cfs.bookmyshow.Security.CustomUserDetailsService;
import com.cfs.bookmyshow.Security.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final CustomUserDetailsService userDetailsService;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(userDetailsService);

        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }


    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration
    ) throws Exception {

        return configuration.getAuthenticationManager();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .cors(cors -> {})

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .authorizeHttpRequests(auth -> auth

                        // ==========================
                        // PUBLIC
                        // ==========================

                        .requestMatchers(
                                "/api/users/register",
                                "/api/users/login"
                        ).permitAll()


                        // ==========================
                        // VIEW DATA
                        // ==========================

                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/movies/**",
                                "/api/cities/**",
                                "/api/theaters/**",
                                "/api/screens/**",
                                "/api/seats/**",
                                "/api/shows/**"
                        ).authenticated()


                        // ==========================
                        // ADMIN - MOVIES
                        // ==========================

                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/movies/**"
                        ).hasRole("ADMIN")

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/movies/**"
                        ).hasRole("ADMIN")

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/movies/**"
                        ).hasRole("ADMIN")


                        // ==========================
                        // ADMIN - CITIES
                        // ==========================

                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/cities/**"
                        ).hasRole("ADMIN")

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/cities/**"
                        ).hasRole("ADMIN")

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/cities/**"
                        ).hasRole("ADMIN")


                        // ==========================
                        // ADMIN - THEATERS
                        // ==========================

                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/theaters/**"
                        ).hasRole("ADMIN")

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/theaters/**"
                        ).hasRole("ADMIN")

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/theaters/**"
                        ).hasRole("ADMIN")


                        // ==========================
                        // ADMIN - SCREENS
                        // ==========================

                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/screens/**"
                        ).hasRole("ADMIN")

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/screens/**"
                        ).hasRole("ADMIN")

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/screens/**"
                        ).hasRole("ADMIN")


                        // ==========================
                        // ADMIN - SEATS
                        // ==========================

                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/seats/**"
                        ).hasRole("ADMIN")

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/seats/**"
                        ).hasRole("ADMIN")

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/seats/**"
                        ).hasRole("ADMIN")


                        // ==========================
                        // ADMIN - SHOWS
                        // ==========================

                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/shows/**"
                        ).hasRole("ADMIN")

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/shows/**"
                        ).hasRole("ADMIN")

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/shows/**"
                        ).hasRole("ADMIN")


                        // ==========================
                        // BOOKINGS
                        // ==========================

                        .requestMatchers(
                                "/api/bookings/**"
                        ).authenticated()


                        // ==========================
                        // USER MANAGEMENT
                        // ==========================

                        .requestMatchers(
                                "/api/users/**"
                        ).hasRole("ADMIN")


                        // ==========================
                        // EVERYTHING ELSE
                        // ==========================

                        .anyRequest().authenticated()
                )

                .authenticationProvider(
                        authenticationProvider()
                )

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}