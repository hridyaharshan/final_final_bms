package com.benchmgmt.config;

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

    // Remove the @Bean method for JwtAuthenticationFilter
    // Keep @Component on JwtAuthenticationFilter class

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           JwtAuthenticationFilter jwtAuthFilter) throws Exception {
        http.cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/bms/admin/login",
                                "/bms/admin/register",
                                "/bms/trainer/login",
                                "/bms/trainer/register",
                                "/bms/interviews/**"
                        ).permitAll()
                        .requestMatchers("/bms/admin/**").hasRole("ADMIN")
                        .requestMatchers("/bms/trainer/**").hasRole("TRAINER")
                        .requestMatchers("/bms/assessment/**", "/bms/scores/**", "/bms/details/**")
                        .hasAnyRole("ADMIN", "TRAINER")
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}