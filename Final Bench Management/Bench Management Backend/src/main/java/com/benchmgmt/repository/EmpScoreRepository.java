package com.benchmgmt.repository;

import com.benchmgmt.dto.ScoreDetailsDTO;
import com.benchmgmt.entity.EmpScore;
import com.benchmgmt.entity.EmpScoreId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface EmpScoreRepository extends JpaRepository<EmpScore, EmpScoreId> {

    @Query("SELECT new com.benchmgmt.dto.ScoreDetailsDTO(c.name, e.empId, e.assessmentId, e.empScore, e.totalScore,a.topic) " +
            "FROM EmpScore e JOIN Candidate c ON e.empId = c.empId " +
            "JOIN Assessment a ON e.assessmentId = a.assessmentId " +
            "WHERE (:topic IS NULL OR a.topic = :topic) " +
            "AND (:empId IS NULL OR e.empId = :empId) " +
            "AND (:date IS NULL OR a.createdDate = :date)")
    List<ScoreDetailsDTO> filterScores(@Param("topic") String topic, @Param("empId") Integer empId, @Param("date") LocalDate date);
}
