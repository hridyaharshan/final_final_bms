package com.benchmgmt.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.benchmgmt.model.Level;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Table(name = "candidates")
public class Candidate {

    @Id
    @Column(name = "emp_id")
    private Integer empId;

    private String name;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate doj;

    @Column(name = "primary_skill")
    private String primarySkill;

    @Enumerated(EnumType.STRING)
    private Level level;

    private String location;

    @Email
    private String email;

    @Column(name = "department_name")
    private String departmentName;

    @CreationTimestamp
    @Column(name = "creation_time", updatable = false)
    private LocalDateTime creationTime;

    @UpdateTimestamp
    @Column(name = "last_update_time")
    private LocalDateTime lastUpdateTime;

    @Column(name = "bench_start_date")
    private LocalDate benchStartDate;

    @Column(name = "bench_end_date")
    private LocalDate benchEndDate;

    @Column(name = "is_deployable")
    private Boolean isDeployable;

    @Column(name = "blocking_status")
    private Boolean blockingStatus;

    // Not persisted in DB
    @Transient
    private Integer agingDays;

    @PostLoad
    @PostPersist
    @PostUpdate
    private void calculateAgingDays() {
        if (benchStartDate != null) {
            LocalDate effectiveEndDate = (benchEndDate != null) ? benchEndDate : LocalDate.now();
            this.agingDays = (int) ChronoUnit.DAYS.between(benchStartDate, effectiveEndDate);
        } else {
            this.agingDays = null;
        }
    }


}
