package com.benchmgmt.controller;

import com.benchmgmt.dto.JwtResponse;
import com.benchmgmt.dto.LoginRequest;
import com.benchmgmt.dto.RegisterTrainerRequest;
import com.benchmgmt.service.TrainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bms/trainer")
@RequiredArgsConstructor
public class TrainerController {

    private final TrainerService trainerService;

//    @PostMapping("/register")
//    public ResponseEntity<String> register(@RequestBody RegisterTrainerRequest request) {
//        String response = trainerService.registerTrainer(request);
//        return ResponseEntity.ok(response);
//    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest request) {
        JwtResponse response = trainerService.login(request);
        return ResponseEntity.ok(response);
    }
}
