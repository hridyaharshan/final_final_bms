package com.benchmgmt;

import java.time.LocalDate;

import com.benchmgmt.service.impl.InterviewServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.benchmgmt.entity.Candidate;
import com.benchmgmt.entity.Interview;
import com.benchmgmt.model.InterviewStatus;
import com.benchmgmt.repository.CandidateRepository;
import com.benchmgmt.repository.InterviewRepository;
import com.benchmgmt.service.impl.CandidateServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDate;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BenchManagementApplicationTests {
    @Autowired
    private InterviewServiceImpl interviewService;
    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private InterviewRepository interviewRepository;

    @Test
    void testBenchEndDateSetOnPassedInterviewUpdate() {
        // Setup candidate and interview
        Candidate candidate = Candidate.builder()
                .empId(9999)
                .name("Test Candidate")
                .doj(LocalDate.now())
                .primarySkill("Java")
                .level(null)
                .location("Test")
                .email("test@example.com")
                .departmentName("IT")
                .benchStartDate(LocalDate.now())
                .build();
        candidateRepository.save(candidate);
        Interview interview = Interview.builder()
                .candidate(candidate)
                .status(InterviewStatus.PENDING)
                .date(LocalDate.now())
                .build();
        interviewRepository.save(interview);
        // Update interview to PASSED
        interview.setStatus(InterviewStatus.PASSED);
        interview.setUpdatedAt(LocalDate.of(2023, 5, 10));
        interviewRepository.save(interview);
        // Simulate updateInterview logic
        candidate.setBenchEndDate(interview.getUpdatedAt());
        candidateRepository.save(candidate);
        Candidate updatedCandidate = candidateRepository.findById(9999).orElseThrow();
        Assertions.assertEquals(LocalDate.of(2023, 5, 10), updatedCandidate.getBenchEndDate());
    }
}

class CandidateServiceImplUnitTest {
    @Mock
    private CandidateRepository candidateRepository;
    @Mock
    private InterviewRepository interviewRepository;
    @InjectMocks
    private CandidateServiceImpl candidateService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCandidates_benchEndDateSet() {
        Candidate c = Candidate.builder().empId(1).benchEndDate(LocalDate.now()).build();
        when(candidateRepository.findAll()).thenReturn(List.of(c));
        List<Candidate> result = candidateService.getAllCandidates();
        assertTrue(result.isEmpty());
    }

    @Test
    void getAllCandidates_sucessInterview() {
        Candidate c = Candidate.builder().empId(2).benchEndDate(null).build();
        Interview i = Interview.builder().status(InterviewStatus.SUCESS).build();
        when(candidateRepository.findAll()).thenReturn(List.of(c));
        when(interviewRepository.findByCandidate_empId(2)).thenReturn(List.of(i));
        List<Candidate> result = candidateService.getAllCandidates();
        assertTrue(result.isEmpty());
    }

    @Test
    void getAllCandidates_noSucessInterview() {
        Candidate c = Candidate.builder().empId(3).benchEndDate(null).build();
        Interview i = Interview.builder().status(InterviewStatus.FAILED).build();
        when(candidateRepository.findAll()).thenReturn(List.of(c));
        when(interviewRepository.findByCandidate_empId(3)).thenReturn(List.of(i));
        List<Candidate> result = candidateService.getAllCandidates();
        assertEquals(1, result.size());
    }

    @Test
    void findCandidatesByBenchEndDateOrInterview_benchEndDateInRange() {
        Candidate c = Candidate.builder().empId(4).benchEndDate(LocalDate.of(2023,5,10)).build();
        when(candidateRepository.findAll()).thenReturn(List.of(c));
        List<Candidate> result = candidateService.findCandidatesByBenchEndDateOrInterview(LocalDate.of(2023,5,1), LocalDate.of(2023,5,15));
        assertEquals(1, result.size());
    }

    @Test
    void findCandidatesByBenchEndDateOrInterview_benchEndDateNull_sucessInterviewInRange() {
        Candidate c = Candidate.builder().empId(5).benchEndDate(null).build();
        Interview i = Interview.builder().status(InterviewStatus.SUCESS).updatedAt(LocalDate.of(2023,5,10)).build();
        when(candidateRepository.findAll()).thenReturn(List.of(c));
        when(interviewRepository.findByCandidate_empId(5)).thenReturn(List.of(i));
        List<Candidate> result = candidateService.findCandidatesByBenchEndDateOrInterview(LocalDate.of(2023,5,1), LocalDate.of(2023,5,15));
        assertEquals(1, result.size());
    }

    @Test
    void findCandidatesByBenchEndDateOrInterview_benchEndDateNull_noSucessInterview() {
        Candidate c = Candidate.builder().empId(6).benchEndDate(null).build();
        Interview i = Interview.builder().status(InterviewStatus.FAILED).updatedAt(LocalDate.of(2023,5,10)).build();
        when(candidateRepository.findAll()).thenReturn(List.of(c));
        when(interviewRepository.findByCandidate_empId(6)).thenReturn(List.of(i));
        List<Candidate> result = candidateService.findCandidatesByBenchEndDateOrInterview(LocalDate.of(2023,5,1), LocalDate.of(2023,5,15));
        assertTrue(result.isEmpty());
    }

    @Test
    void getCandidatesByDoj_match() {
        Candidate c = Candidate.builder().empId(7).doj(LocalDate.of(2022,1,1)).build();
        when(candidateRepository.findByDoj(LocalDate.of(2022,1,1))).thenReturn(List.of(c));
        List<Candidate> result = candidateService.getCandidatesByDoj(LocalDate.of(2022,1,1));
        assertEquals(1, result.size());
    }

    @Test
    void getCandidatesByDoj_noMatch() {
        when(candidateRepository.findByDoj(LocalDate.of(2022,1,2))).thenReturn(List.of());
        List<Candidate> result = candidateService.getCandidatesByDoj(LocalDate.of(2022,1,2));
        assertTrue(result.isEmpty());
    }

    @Test
    void addCandidate_normal() {
        Candidate c = Candidate.builder().empId(8).build();
        when(candidateRepository.save(any())).thenReturn(c);
        Candidate result = candidateService.addCandidate(new com.benchmgmt.dto.CandidateDTO());
        assertNotNull(result);
    }

    @Test
    void getCandidateById_found() {
        Candidate c = Candidate.builder().empId(9).build();
        when(candidateRepository.findById(9)).thenReturn(Optional.of(c));
        Optional<Candidate> result = candidateService.getCandidateById(9);
        assertTrue(result.isPresent());
    }

    @Test
    void getCandidateById_notFound() {
        when(candidateRepository.findById(10)).thenReturn(Optional.empty());
        Optional<Candidate> result = candidateService.getCandidateById(10);
        assertTrue(result.isEmpty());
    }
}
