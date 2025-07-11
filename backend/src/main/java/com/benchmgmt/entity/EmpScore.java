package com.benchmgmt.entity;

import jakarta.persistence.*;

@Entity
@IdClass(EmpScoreId.class)
@Table(name = "emp_score")
public class EmpScore {

    @Id
    @Column(name = "emp_id")
    private Integer empId;

    @Id
    @Column(name = "assessment_id")
    private Integer assessmentId;

    @Column(name = "emp_score")
    private Integer empScore;

    @Column(name = "total_score")
    private Integer totalScore;

    // Use 'assessment' as the entity relation
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assessment_id", referencedColumnName = "assessment_id", insertable = false, updatable = false)
    private Assessment assessment;

    // Use 'candidate' as the entity relation
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_id", referencedColumnName = "emp_id", insertable = false, updatable = false)
    private Candidate candidate;

    // --- Getters and Setters ---

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

    public Assessment getAssessment() {
        return assessment;
    }

    public void setAssessment(Assessment assessment) {
        this.assessment = assessment;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }
}
