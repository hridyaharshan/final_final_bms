package com.benchmgmt.config;

import com.benchmgmt.repository.AdminRepository;
import com.benchmgmt.repository.TrainerRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(
            JwtService jwtService,
            AdminRepository adminRepo,
            TrainerRepository trainerRepo
    ) {
        return new JwtAuthenticationFilter(jwtService, adminRepo, trainerRepo);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints - these should bypass JWT entirely
                        .requestMatchers(
                                "/bms/admin/login",
                                "/bms/admin/register",
                                "/bms/trainer/login",
                                "/bms/trainer/register",
                                "/bms/interviews/**"
                        ).permitAll()

                        // Role-based access control
                        .requestMatchers("/bms/admin/**").hasRole("ADMIN")
                        .requestMatchers("/bms/trainer/**").hasRole("TRAINER")
                        .requestMatchers("/bms/assessment/**", "/bms/scores/**", "/bms/details/**")
                        .hasAnyRole("ADMIN", "TRAINER")

                        // All other requests require authentication
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // Add JWT filter before the default authentication filter
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
