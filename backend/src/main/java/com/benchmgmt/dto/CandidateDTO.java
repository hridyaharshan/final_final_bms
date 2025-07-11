package com.benchmgmt.dto;

import com.benchmgmt.model.Level;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CandidateDTO {

    @NotNull
    private Integer empId;

    @NotBlank
    private String name;

    @NotNull
    private LocalDate doj;

    @NotBlank
    private String primarySkill;

    @NotNull
    private Level level;

    @NotBlank
    private String location;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String departmentName;

    private LocalDate benchStartDate;
    private LocalDate benchEndDate;

    private Boolean isDeployable;
    private Boolean blockingStatus;
}
