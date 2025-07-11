package com.benchmgmt.repository;

import com.benchmgmt.entity.Interview;
import com.benchmgmt.model.InterviewStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterviewRepository extends JpaRepository<Interview, Integer> {

    // ✅ Correct: navigating to candidate's ID
    List<Interview> findByCandidate_empId(Integer empId); // ✅ CORRECT


    List<Interview> findByStatus(InterviewStatus status);

    List<Interview> findByDepartment(String department);
}
