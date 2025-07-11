package com.benchmgmt.controller;

import com.benchmgmt.dto.JwtResponse;
import com.benchmgmt.dto.LoginRequest;
import com.benchmgmt.dto.PreApprovedEmailRequest;
import com.benchmgmt.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bms/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> loginAdmin(@RequestBody LoginRequest request) {
        JwtResponse response = adminService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/add-trainer-email")
    public ResponseEntity<String> addTrainerEmail(@RequestBody PreApprovedEmailRequest request) {
        String response = adminService.addPreApprovedEmail(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/trainer-emails")
    public ResponseEntity<?> getAllTrainerEmails() {
        return ResponseEntity.ok(adminService.getAllPreApprovedEmails());
    }
}
