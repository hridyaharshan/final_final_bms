package com.benchmgmt.service;

import com.benchmgmt.dto.InterviewCycleDTO;
import com.benchmgmt.dto.InterviewDTO;
import com.benchmgmt.dto.InterviewRoundDTO;

import java.util.List;


public interface InterviewService {
    InterviewDTO saveInterview(InterviewDTO dto);
    InterviewDTO getInterviewById(Integer id);

    List<InterviewDTO> getInterviewsByCandidateId(Integer empId);


    List<InterviewDTO> getAllInterviews();
    InterviewDTO updateInterview(Integer id, InterviewDTO dto);
    void deleteInterview(Integer id);
    public List<InterviewRoundDTO> getInterviewsByCycleId(Integer cycleId);
    InterviewCycleDTO createInterviewCycle(InterviewCycleDTO dto);
    InterviewDTO saveInterviewForCycle(Long cycleId, InterviewDTO dto);
    InterviewDTO saveInterviewRoundForCycle(Long cycleId, InterviewDTO dto);

    List<InterviewDTO> getFullInterviewsByCandidateAndCycle(Integer empId, Integer cycleId);
    InterviewDTO addInterviewRoundToCycle(Integer empId, Integer cycleId, InterviewDTO dto);


    List<InterviewCycleDTO> getInterviewCyclesByCandidateId(Integer id);

    List<InterviewDTO> getFullInterviewsByCycleId(Integer cycleId);
    void deleteInterviewCycle(Integer cycleId);
    void deleteInterviewRound(Integer interviewId);

}

