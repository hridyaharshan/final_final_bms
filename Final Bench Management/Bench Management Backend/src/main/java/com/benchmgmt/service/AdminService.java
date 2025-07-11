package com.benchmgmt.service;

import com.benchmgmt.dto.JwtResponse;
import com.benchmgmt.dto.LoginRequest;
import com.benchmgmt.dto.PreApprovedEmailRequest;
import com.benchmgmt.entity.Admin;
import com.benchmgmt.entity.PreApprovedEmail;
import com.benchmgmt.repository.AdminRepository;
import com.benchmgmt.repository.PreApprovedEmailRepository;
import com.benchmgmt.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final PreApprovedEmailRepository preApprovedEmailRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    // REMOVED: AuthenticationManager - not needed for JWT authentication

    public JwtResponse login(LoginRequest request) {
        // Validate input
        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            throw new RuntimeException("Email is required");
        }
        if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
            throw new RuntimeException("Password is required");
        }

        System.out.println("Trying login: " + request.getEmail());

        // Find admin by email
        Admin admin = adminRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        // Verify password manually
        if (!passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        // Generate JWT token
        String token = jwtService.generateToken(admin.getEmail(), "ADMIN");

        return new JwtResponse(token);
    }

    public String addPreApprovedEmail(PreApprovedEmailRequest request) {
        if (preApprovedEmailRepository.existsByEmail(request.getEmail())) {
            return "Email already exists";
        }
        PreApprovedEmail email = new PreApprovedEmail();
        email.setEmail(request.getEmail());
        preApprovedEmailRepository.save(email);
        return "Trainer email added successfully";
    }

    public List<String> getAllPreApprovedEmails() {
        return preApprovedEmailRepository.findAll()
                .stream()
                .map(PreApprovedEmail::getEmail)
                .toList();
    }
}