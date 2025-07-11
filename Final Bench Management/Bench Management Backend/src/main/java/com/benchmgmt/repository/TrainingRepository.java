package com.benchmgmt.repository;

import com.benchmgmt.entity.Training;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainingRepository extends JpaRepository<Training,Integer> {
    List<Training> findByCandidateEmpId(Integer empId);
}
