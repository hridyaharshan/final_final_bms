package com.benchmgmt.dto;

public class EmpScoreRequest {
    private Integer empId;
    private Integer assessmentId;
    private Integer empScore;
    private Integer totalScore;

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

