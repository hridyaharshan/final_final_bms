package com.benchmgmt.dto;

import java.time.LocalDate;

public class AssessmentDTO {
    private String topic;
    private String assessmentLink;
    private LocalDate date;

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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
