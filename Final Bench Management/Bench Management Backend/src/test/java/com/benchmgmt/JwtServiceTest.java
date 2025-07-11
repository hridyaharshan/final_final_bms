package com.benchmgmt;

import com.benchmgmt.config.JwtService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import javax.crypto.SecretKey;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class JwtServiceTest {

    private JwtService jwtService;
    private SecretKey key;
    private String secret;
    private final long expirationMs = 1000 * 60 * 60; // 1 hour

    @BeforeEach
    void setUp() {
        key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        secret = java.util.Base64.getEncoder().encodeToString(key.getEncoded());
        jwtService = new JwtService();
        ReflectionTestUtils.setField(jwtService, "secret", secret);
        ReflectionTestUtils.setField(jwtService, "expirationMs", expirationMs);
    }

    @Test
    @DisplayName("extractUsername should return the subject from a valid JWT")
    void extractUsername_ReturnsSubject() {
        String email = "user@example.com";
        String token = Jwts.builder()
                .setSubject(email)
                .claim("role", "ADMIN")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
        String extracted = jwtService.extractUsername(token);
        assertEquals(email, extracted);
    }

    @Test
    @DisplayName("extractTokenFromRequest should extract token from Authorization header")
    void extractTokenFromRequest_ValidHeader() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("Authorization")).thenReturn("Bearer sometoken123");
        String token = jwtService.extractTokenFromRequest(request);
        assertEquals("sometoken123", token);
    }

    @Test
    @DisplayName("extractTokenFromRequest should return null if header is missing")
    void extractTokenFromRequest_NoHeader() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("Authorization")).thenReturn(null);
        String token = jwtService.extractTokenFromRequest(request);
        assertNull(token);
    }

    @Test
    @DisplayName("extractTokenFromRequest should return null if header does not start with Bearer")
    void extractTokenFromRequest_InvalidHeader() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("Authorization")).thenReturn("Basic sometoken123");
        String token = jwtService.extractTokenFromRequest(request);
        assertNull(token);
    }
} 