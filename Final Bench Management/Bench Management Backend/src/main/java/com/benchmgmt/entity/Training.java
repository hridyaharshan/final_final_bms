package com.benchmgmt.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer trainingId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String mentor;
    @Lob
    private String feedback;
    @Lob
    private String topics;

    @ManyToOne
    @JoinColumn(name = "emp_id")
    private Candidate candidate;
}
