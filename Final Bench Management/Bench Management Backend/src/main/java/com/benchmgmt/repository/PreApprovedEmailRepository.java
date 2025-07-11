package com.benchmgmt.repository;


import com.benchmgmt.entity.PreApprovedEmail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PreApprovedEmailRepository extends JpaRepository<PreApprovedEmail, String> {
    void deleteByEmail(String email);
    boolean existsByEmail(String email);
    List<PreApprovedEmail> findAll();

}