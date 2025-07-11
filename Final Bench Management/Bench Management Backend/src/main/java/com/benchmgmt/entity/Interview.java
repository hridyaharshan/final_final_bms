package com.benchmgmt.entity;



import com.benchmgmt.model.Feedback;
import com.benchmgmt.model.InterviewStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Interview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer interviewId;

    @ManyToOne
    @JoinColumn(name = "candidate_id") // ❗ Important: do not put @GeneratedValue here
    private Candidate candidate;

    private LocalDate date;
    private String panel;

    @Enumerated(EnumType.STRING)
    private InterviewStatus status;

    @Enumerated(EnumType.STRING)
    private Feedback feedback;

    private String detailedFeedback;
    private String review;
    private int round;
    private String department;
    private String client;

    private LocalDate createdAt;
    private LocalDate updatedAt;

    @PrePersist
    void onCreate() {
        this.createdAt = LocalDate.now();
    }

    @PreUpdate
    void onUpdate() {
        this.updatedAt = LocalDate.now();
    }

    @ManyToOne
    @JoinColumn(name = "interview_cycle_id") // ❗ Just a join column, no auto generation
    private InterviewCycle interviewCycle;
}

