package com.benchmgmt.dto;

import java.time.LocalDate;

public class AssessmentResponseDTO {
    private Integer assessmentId;
    private String topic;
    private String assessmentLink;
    private LocalDate createdDate;
    private Integer trainerId;
    private String trainerName;

    public AssessmentResponseDTO(Integer assessmentId, String topic, String assessmentLink,
                                 LocalDate createdDate, Integer trainerId, String trainerName) {
        this.assessmentId = assessmentId;
        this.topic = topic;
        this.assessmentLink = assessmentLink;
        this.createdDate = createdDate;
        this.trainerId = trainerId;
        this.trainerName = trainerName;
    }

    public Integer getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(Integer assessmentId) {
        this.assessmentId = assessmentId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getAssessmentLink() {
        return assessmentLink;
    }

    public void setAssessmentLink(String assessmentLink) {
        this.assessmentLink = assessmentLink;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(Integer trainerId) {
        this.trainerId = trainerId;
    }

    public String getTrainerName() {
        return trainerName;
    }

    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }
}

