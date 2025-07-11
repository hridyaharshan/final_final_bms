package com.benchmgmt.repository;

import com.benchmgmt.entity.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface TrainerRepository extends JpaRepository<Trainer, Integer> {
    Optional<Trainer> findByEmail(String email);
}