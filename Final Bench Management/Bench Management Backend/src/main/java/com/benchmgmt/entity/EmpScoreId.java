package com.benchmgmt.entity;

import java.io.Serializable;
import java.util.Objects;

public class EmpScoreId implements Serializable {
    private Integer empId;
    private Integer assessmentId;

    public EmpScoreId() {}

    public EmpScoreId(Integer empId, Integer assessmentId) {
        this.empId = empId;
        this.assessmentId = assessmentId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmpScoreId)) return false;
        EmpScoreId that = (EmpScoreId) o;
        return Objects.equals(empId, that.empId) && Objects.equals(assessmentId, that.assessmentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(empId, assessmentId);
    }
}
