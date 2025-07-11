package com.benchmgmt.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.benchmgmt.dto.CandidateDTO;
import com.benchmgmt.entity.Candidate;
import com.benchmgmt.entity.Interview;
import com.benchmgmt.model.InterviewStatus;
import com.benchmgmt.repository.CandidateRepository;
import com.benchmgmt.repository.InterviewRepository;
import com.benchmgmt.service.CandidateService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepository repository;
    private final InterviewRepository interviewRepository;

    // Helper method to check if interview is successful
    private boolean isInterviewSuccessful(InterviewStatus status) {
        return status == InterviewStatus.PASSED || status == InterviewStatus.SUCESS;
    }

    @Override
    public Candidate addCandidate(CandidateDTO dto) {
        Candidate candidate = Candidate.builder()
                .empId(dto.getEmpId())
                .name(dto.getName())
                .doj(dto.getDoj())
                .primarySkill(dto.getPrimarySkill())
                .level(dto.getLevel())
                .location(dto.getLocation())
                .email(dto.getEmail())
                .departmentName(dto.getDepartmentName())
                .benchStartDate(dto.getBenchStartDate())
                .benchEndDate(dto.getBenchEndDate())
                .isDeployable(dto.getIsDeployable())
                .blockingStatus(dto.getBlockingStatus())
                .build();
        return repository.save(candidate);
    }

    @Override
    public List<Candidate> getAllCandidates() {
        List<Candidate> allCandidates = repository.findAll();
        List<Candidate> filtered = new java.util.ArrayList<>();

        for (Candidate candidate : allCandidates) {
            // Only candidates with no bench_end_date
            if (candidate.getBenchEndDate() == null) {
                // Check if candidate has any successful interview
                boolean hasSuccessfulInterview = interviewRepository.findByCandidate_empId(candidate.getEmpId())
                        .stream()
                        .anyMatch(i -> isInterviewSuccessful(i.getStatus()));

                if (!hasSuccessfulInterview) {
                    filtered.add(candidate);
                }
            }
        }
        return filtered;
    }

    @Override
    public Optional<Candidate> getCandidateById(Integer empId) {
        return repository.findById(empId);
    }

    @Override
    public List<Candidate> getCandidatesByDoj(LocalDate doj) {
        return repository.findByDoj(doj);
    }

    @Override
    public List<Candidate> findCandidatesByBenchEndDateOrInterview(LocalDate start, LocalDate end) {
        List<Candidate> allCandidates = repository.findAll();
        List<Candidate> result = new java.util.ArrayList<>();

        for (Candidate candidate : allCandidates) {
            LocalDate benchEndDate = candidate.getBenchEndDate();

            // Check if bench end date is within range
            if (benchEndDate != null && !benchEndDate.isBefore(start) && !benchEndDate.isAfter(end)) {
                result.add(candidate);
                continue;
            }

            // If benchEndDate is null, check interviews
            if (benchEndDate == null) {
                List<Interview> interviews = interviewRepository.findByCandidate_empId(candidate.getEmpId());

                // Filter successful interviews and get the latest one
                Optional<Interview> latestSuccessful = interviews.stream()
                        .filter(i -> isInterviewSuccessful(i.getStatus()))
                        .max(java.util.Comparator.comparing(Interview::getUpdatedAt));

                if (latestSuccessful.isPresent()) {
                    LocalDate updatedAt = latestSuccessful.get().getUpdatedAt();
                    if (updatedAt != null && !updatedAt.isBefore(start) && !updatedAt.isAfter(end)) {
                        result.add(candidate);
                    }
                }
            }
        }
        return result;
    }
}