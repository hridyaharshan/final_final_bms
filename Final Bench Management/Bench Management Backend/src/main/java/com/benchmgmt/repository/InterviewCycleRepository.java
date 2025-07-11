package com.benchmgmt.repository;

import com.benchmgmt.entity.InterviewCycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterviewCycleRepository extends JpaRepository<InterviewCycle, Integer> {

    // 🔍 Get all interview cycles by candidate employee ID
    List<InterviewCycle> findByCandidate_empId(Integer empId);
}