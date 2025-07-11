package com.benchmgmt.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.benchmgmt.dto.InterviewCycleDTO;
import com.benchmgmt.dto.InterviewDTO;
import com.benchmgmt.dto.InterviewRoundDTO;
import com.benchmgmt.entity.Candidate;
import com.benchmgmt.entity.Interview;
import com.benchmgmt.entity.InterviewCycle;
import com.benchmgmt.model.InterviewStatus;
import com.benchmgmt.repository.CandidateRepository;
import com.benchmgmt.repository.InterviewCycleRepository;
import com.benchmgmt.repository.InterviewRepository;
import com.benchmgmt.service.InterviewService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InterviewServiceImpl implements InterviewService {

    private final InterviewRepository interviewRepository;
    private final CandidateRepository candidateRepository;
    private final InterviewCycleRepository interviewCycleRepository;

    // Helper method to check if interview is successful
    private boolean isInterviewSuccessful(InterviewStatus status) {
        return status == InterviewStatus.PASSED || status == InterviewStatus.SUCESS;
    }

    // Helper method to update bench end date
    private void updateBenchEndDateIfSuccessful(Interview interview) {
        if (isInterviewSuccessful(interview.getStatus())) {
            Candidate candidate = interview.getCandidate();
            // Use current date instead of updatedAt to avoid null issues
            candidate.setBenchEndDate(LocalDate.now());
            candidateRepository.save(candidate);
        }
    }

    @Override
    public InterviewDTO saveInterview(InterviewDTO dto) {
        Interview interview = toEntity(dto);
        Interview saved = interviewRepository.save(interview);

        // Update bench_end_date if interview is successful
        updateBenchEndDateIfSuccessful(saved);

        return toDTO(saved);
    }

    @Override
    public InterviewDTO getInterviewById(Integer id) {
        Interview interview = interviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Interview not found"));
        return toDTO(interview);
    }

    @Override
    public List<InterviewDTO> getAllInterviews() {
        return interviewRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<InterviewDTO> getInterviewsByCandidateId(Integer empId) {
        return interviewRepository.findByCandidate_empId(empId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public InterviewDTO updateInterview(Integer id, InterviewDTO dto) {
        Interview interview = interviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Interview not found"));

        Candidate candidate = candidateRepository.findById(dto.getEmpId())
                .orElseThrow(() -> new RuntimeException("Candidate not found"));

        InterviewCycle cycle = interviewCycleRepository.findById(Math.toIntExact(dto.getCycleId()))
                .orElseThrow(() -> new RuntimeException("Interview cycle not found"));

        interview.setCandidate(candidate);
        interview.setInterviewCycle(cycle);
        interview.setDate(dto.getDate());
        interview.setPanel(dto.getPanel());
        interview.setStatus(dto.getStatus());
        interview.setFeedback(dto.getFeedback());
        interview.setDetailedFeedback(dto.getDetailedFeedback());
        interview.setReview(dto.getReview());
        interview.setRound(dto.getRound());
        interview.setDepartment(dto.getDepartment());
        interview.setClient(dto.getClient());

        Interview updated = interviewRepository.save(interview);

        // Update bench_end_date if interview is successful
        updateBenchEndDateIfSuccessful(updated);

        return toDTO(updated);
    }

    @Override
    public void deleteInterview(Integer id) {
        interviewRepository.deleteById(id);
    }

    public List<InterviewCycleDTO> getInterviewCyclesByCandidateId(Integer empId) {
        return interviewCycleRepository.findByCandidate_empId(empId)
                .stream()
                .map(c -> InterviewCycleDTO.builder()
                        .cycleId(c.getCycleId())
                        .title(c.getTitle())
                        .client(c.getClient())
                        .empId(c.getCandidate().getEmpId())
                        .build())
                .toList();
    }

    @Override
    public List<InterviewRoundDTO> getInterviewsByCycleId(Integer cycleId) {
        InterviewCycle cycle = interviewCycleRepository.findById(cycleId)
                .orElseThrow(() -> new RuntimeException("Cycle not found"));

        return cycle.getInterviews().stream()
                .map(i -> InterviewRoundDTO.builder()
                        .round(i.getRound())
                        .panel(i.getPanel())
                        .status(i.getStatus().name())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public InterviewDTO saveInterviewForCycle(Long cycleId, InterviewDTO dto) {
        Candidate candidate = candidateRepository.findById(dto.getEmpId())
                .orElseThrow(() -> new RuntimeException("Candidate not found"));

        InterviewCycle cycle = interviewCycleRepository.findById(Math.toIntExact(cycleId))
                .orElseThrow(() -> new RuntimeException("Interview cycle not found"));

        Interview interview = Interview.builder()
                .candidate(candidate)
                .interviewCycle(cycle)
                .date(dto.getDate())
                .panel(dto.getPanel())
                .status(dto.getStatus())
                .feedback(dto.getFeedback())
                .detailedFeedback(dto.getDetailedFeedback())
                .review(dto.getReview())
                .round(dto.getRound())
                .department(dto.getDepartment())
                .client(dto.getClient())
                .build();

        Interview saved = interviewRepository.save(interview);

        // Update bench_end_date if interview is successful
        updateBenchEndDateIfSuccessful(saved);

        return toDTO(saved);
    }

    @Override
    public void deleteInterviewCycle(Integer cycleId) {
        InterviewCycle cycle = interviewCycleRepository.findById(cycleId)
                .orElseThrow(() -> new RuntimeException("Interview cycle not found"));

        // Optional: delete associated rounds first (if cascade not set)
        interviewRepository.deleteAll(cycle.getInterviews());

        interviewCycleRepository.deleteById(cycleId);
    }

    @Override
    public void deleteInterviewRound(Integer interviewId) {
        if (!interviewRepository.existsById(interviewId)) {
            throw new RuntimeException("Interview round not found");
        }
        interviewRepository.deleteById(interviewId);
    }

    @Override
    public InterviewDTO saveInterviewRoundForCycle(Long cycleId, InterviewDTO dto) {
        Candidate candidate = candidateRepository.findById(dto.getEmpId())
                .orElseThrow(() -> new RuntimeException("Candidate not found"));

        InterviewCycle cycle = interviewCycleRepository.findById(Math.toIntExact(cycleId))
                .orElseThrow(() -> new RuntimeException("Interview cycle not found"));

        Interview interview = Interview.builder()
                .candidate(candidate)
                .interviewCycle(cycle)
                .date(dto.getDate())
                .panel(dto.getPanel())
                .status(dto.getStatus())
                .feedback(dto.getFeedback())
                .detailedFeedback(dto.getDetailedFeedback())
                .review(dto.getReview())
                .round(dto.getRound())
                .department(dto.getDepartment())
                .client(dto.getClient())
                .build();

        Interview saved = interviewRepository.save(interview);

        // Update bench_end_date if interview is successful
        updateBenchEndDateIfSuccessful(saved);

        return toDTO(saved);
    }

    @Override
    public List<InterviewDTO> getFullInterviewsByCycleId(Integer cycleId) {
        InterviewCycle cycle = interviewCycleRepository.findById(cycleId)
                .orElseThrow(() -> new RuntimeException("Cycle not found"));

        return cycle.getInterviews().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<InterviewDTO> getFullInterviewsByCandidateAndCycle(Integer empId, Integer cycleId) {
        InterviewCycle cycle = interviewCycleRepository.findById(cycleId)
                .orElseThrow(() -> new RuntimeException("Cycle not found"));

        if (!cycle.getCandidate().getEmpId().equals(empId)) {
            throw new RuntimeException("Cycle does not belong to the candidate");
        }

        return cycle.getInterviews().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public InterviewDTO addInterviewRoundToCycle(Integer empId, Integer cycleId, InterviewDTO dto) {
        Candidate candidate = candidateRepository.findById(empId)
                .orElseThrow(() -> new RuntimeException("Candidate not found"));

        InterviewCycle cycle = interviewCycleRepository.findById(cycleId)
                .orElseThrow(() -> new RuntimeException("Cycle not found"));

        if (!cycle.getCandidate().getEmpId().equals(empId)) {
            throw new RuntimeException("Cycle does not belong to the candidate");
        }

        Interview interview = Interview.builder()
                .candidate(candidate)
                .interviewCycle(cycle)
                .date(dto.getDate())
                .panel(dto.getPanel())
                .status(dto.getStatus())
                .feedback(dto.getFeedback())
                .detailedFeedback(dto.getDetailedFeedback())
                .review(dto.getReview())
                .round(dto.getRound())
                .department(dto.getDepartment())
                .client(dto.getClient())
                .build();

        Interview saved = interviewRepository.save(interview);

        // Update bench_end_date if interview is successful
        updateBenchEndDateIfSuccessful(saved);

        return toDTO(saved);
    }

    // ----------- Mapping Methods -----------

    private InterviewDTO toDTO(Interview entity) {
        return InterviewDTO.builder()
                .interviewId(entity.getInterviewId())
                .empId(entity.getCandidate().getEmpId())
                .cycleId(entity.getInterviewCycle() != null ? entity.getInterviewCycle().getCycleId() : null)
                .date(entity.getDate())
                .panel(entity.getPanel())
                .status(entity.getStatus())
                .feedback(entity.getFeedback())
                .detailedFeedback(entity.getDetailedFeedback())
                .review(entity.getReview())
                .round(entity.getRound())
                .department(entity.getDepartment())
                .client(entity.getClient())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    private Interview toEntity(InterviewDTO dto) {
        Candidate candidate = candidateRepository.findById(dto.getEmpId())
                .orElseThrow(() -> new RuntimeException("Candidate not found"));

        InterviewCycle cycle = interviewCycleRepository.findById(Math.toIntExact(dto.getCycleId()))
                .orElseThrow(() -> new RuntimeException("Interview cycle not found"));

        return Interview.builder()
                .interviewId(dto.getInterviewId())
                .candidate(candidate)
                .interviewCycle(cycle)
                .date(dto.getDate())
                .panel(dto.getPanel())
                .status(dto.getStatus())
                .feedback(dto.getFeedback())
                .detailedFeedback(dto.getDetailedFeedback())
                .review(dto.getReview())
                .round(dto.getRound())
                .department(dto.getDepartment())
                .client(dto.getClient())
                .build();
    }

    @Override
    public InterviewCycleDTO createInterviewCycle(InterviewCycleDTO dto) {
        Candidate candidate = candidateRepository.findById(dto.getEmpId())
                .orElseThrow(() -> new RuntimeException("Candidate not found"));

        InterviewCycle cycle = InterviewCycle.builder()
                .title(dto.getTitle())
                .client(dto.getClient())
                .startedAt(LocalDate.now())
                .candidate(candidate)
                .build();

        InterviewCycle saved = interviewCycleRepository.save(cycle);

        return InterviewCycleDTO.builder()
                .cycleId(saved.getCycleId())
                .title(saved.getTitle())
                .client(saved.getClient())
                .empId(saved.getCandidate().getEmpId())
                .build();
    }
}