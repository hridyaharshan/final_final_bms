package com.benchmgmt.service;

import com.benchmgmt.dto.EmpScoreRequest;
import com.benchmgmt.dto.ScoreDetailsDTO;
import com.benchmgmt.entity.EmpScore;
import com.benchmgmt.repository.EmpScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmpScoreService {

    @Autowired
    private EmpScoreRepository repo;

    public EmpScore addScore(EmpScoreRequest dto) {
        EmpScore score = new EmpScore();
        score.setEmpId(dto.getEmpId());
        score.setAssessmentId(dto.getAssessmentId());
        score.setEmpScore(dto.getEmpScore());
        score.setTotalScore(dto.getTotalScore());
        return repo.save(score);
    }

    public List<ScoreDetailsDTO> getFilteredScores(String topic, Integer empId, LocalDate date) {
        return repo.filterScores(topic, empId, date);
    }
}