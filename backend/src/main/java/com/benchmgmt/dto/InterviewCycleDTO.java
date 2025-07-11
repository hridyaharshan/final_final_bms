package com.benchmgmt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InterviewCycleDTO {
    private Long cycleId;
    private String client;
    private String title;
    private Integer empId;
}



