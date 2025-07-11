package com.benchmgmt.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.benchmgmt.dto.CandidateDTO;
import com.benchmgmt.entity.Candidate;
import com.benchmgmt.service.CandidateService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/bms")
@RequiredArgsConstructor
public class CandidateController {

    private final CandidateService service;

    @PostMapping("/candidate")
    public ResponseEntity<Candidate> addCandidate(@Valid @RequestBody CandidateDTO dto) {
        return ResponseEntity.ok(service.addCandidate(dto));
    }

    @GetMapping("/details")
    public ResponseEntity<List<Candidate>> getDetails() {
        return ResponseEntity.ok(service.getAllCandidates());
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<?> getCandidateById(@PathVariable("id") Integer id) {
        return service.getCandidateById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/details/filter")
    public ResponseEntity<List<Candidate>> getCandidatesByDoj(
            @RequestParam("doj") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate doj) {
        return ResponseEntity.ok(service.getCandidatesByDoj(doj));
    }

    @GetMapping("/details/bench-end-date-range")
    public ResponseEntity<List<Candidate>> getCandidatesByBenchEndDateOrInterview(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return ResponseEntity.ok(service.findCandidatesByBenchEndDateOrInterview(start, end));
    }

    @GetMapping("/")
    public String healthCheck() {
        return "Candidate API is running!";
    }
}
