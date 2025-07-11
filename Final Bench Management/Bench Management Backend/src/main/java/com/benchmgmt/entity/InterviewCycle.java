package com.benchmgmt.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InterviewCycle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cycleId;

    private String client;
    private String title;
    private LocalDate startedAt;

    @ManyToOne
    @JoinColumn(name = "candidate_id") // ❗ Just reference, no generation
    private Candidate candidate;

    @OneToMany(mappedBy = "interviewCycle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Interview> interviews = new ArrayList<>();
}
