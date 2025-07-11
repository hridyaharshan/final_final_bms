package com.benchmgmt.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "assessment")
@Data
public class Assessment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assessment_id")
    private Integer assessmentId;

    @Column(name = "assessment_link")
    private String assessmentLink;

    @Column(name = "topic")
    private String topic;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainer_id", referencedColumnName = "emp_id", nullable = false)
    private Trainer trainer;

    // Automatically set the current date before persisting
    @PrePersist
    public void setCreatedDateIfNull() {
        if (this.createdDate == null) {
            this.createdDate = LocalDate.now();
        }
    }

    // --- Getters and Setters ---

    public Integer getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(Integer assessmentId) {
        this.assessmentId = assessmentId;
    }

    public String getAssessmentLink() {
        return assessmentLink;
    }

    public void setAssessmentLink(String assessmentLink) {
        this.assessmentLink = assessmentLink;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }
}

