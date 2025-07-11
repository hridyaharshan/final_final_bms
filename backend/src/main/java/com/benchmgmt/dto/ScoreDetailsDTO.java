package com.benchmgmt.dto;

public class ScoreDetailsDTO {
    private String name;
    private Integer empId;
    private Integer assessmentId;
    private Integer empScore;
    private Integer totalScore;
    private String topic;
    public ScoreDetailsDTO(String name, Integer empId, Integer assessmentId, Integer empScore, Integer totalScore,String topic) {
        this.name = name;
        this.empId = empId;
        this.assessmentId = assessmentId;
        this.empScore = empScore;
        this.totalScore = totalScore;
        this.topic=topic;
    }
    public  String getTopic(){
        return topic;
    }
    public void setTopic(String topic)
    {
        this.topic=topic;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public Integer getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(Integer assessmentId) {
        this.assessmentId = assessmentId;
    }

    public Integer getEmpScore() {
        return empScore;
    }

    public void setEmpScore(Integer empScore) {
        this.empScore = empScore;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }
}
