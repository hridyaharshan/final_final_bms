package com.benchmgmt.service;

import com.benchmgmt.dto.JwtResponse;
import com.benchmgmt.dto.LoginRequest;
import com.benchmgmt.dto.RegisterRequest;
import com.benchmgmt.dto.RegisterTrainerRequest;
import com.benchmgmt.entity.PreApprovedEmail;
import com.benchmgmt.entity.Trainer;
import com.benchmgmt.repository.PreApprovedEmailRepository;
import com.benchmgmt.repository.TrainerRepository;
import com.benchmgmt.config.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
// Example TrainerService with similar fixes
@Service
@RequiredArgsConstructor
public class TrainerService {

    private final TrainerRepository trainerRepository;
    private final PreApprovedEmailRepository preApprovedEmailRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public JwtResponse login(LoginRequest request) {
        // Validate input
        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            throw new RuntimeException("Email is required");
        }
        if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
            throw new RuntimeException("Password is required");
        }

        System.out.println("Trying trainer login: " + request.getEmail());

        // Find trainer by email
        Trainer trainer = trainerRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        // Verify password manually
        if (!passwordEncoder.matches(request.getPassword(), trainer.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        // Generate JWT token
        String token = jwtService.generateToken(trainer.getEmail(), "TRAINER");

        return new JwtResponse(token);
    }

    public JwtResponse register(RegisterRequest request) {
        // Validate input
        if (request.getUsername() == null || request.getUsername().trim().isEmpty()) {
            throw new RuntimeException("Email is required");
        }
        if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
            throw new RuntimeException("Password is required");
        }

        // Check if email is pre-approved
        if (!preApprovedEmailRepository.existsByEmail(request.getUsername())) {
            throw new RuntimeException("Email is not pre-approved for registration");
        }


        // Create new trainer
        Trainer trainer = new Trainer();
        trainer.setEmail(request.getUsername());
        trainer.setPassword(passwordEncoder.encode(request.getPassword()));
        trainer.setName(request.getUsername()); // assuming you have name in RegisterRequest

        trainerRepository.save(trainer);

        // Generate JWT token
        String token = jwtService.generateToken(trainer.getEmail(), "TRAINER");

        return new JwtResponse(token);
    }
}