package com.benchmgmt.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.benchmgmt.dto.CandidateDTO;
import com.benchmgmt.entity.Candidate;

public interface CandidateService {
    Candidate addCandidate(CandidateDTO dto);
    List<Candidate> getAllCandidates();
    Optional<Candidate> getCandidateById(Integer empId);
    List<Candidate> getCandidatesByDoj(LocalDate doj);
    List<Candidate> findCandidatesByBenchEndDateOrInterview(LocalDate start, LocalDate end);
}
