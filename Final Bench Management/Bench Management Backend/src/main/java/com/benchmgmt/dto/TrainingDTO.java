package com.benchmgmt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainingDTO {
    private Integer trainingId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String mentor;
    private String feedback;
    private String topics;

    private Integer empId;
}
