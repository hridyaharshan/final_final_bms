package com.benchmgmt.dto;


import com.benchmgmt.model.Feedback;
import com.benchmgmt.model.InterviewStatus;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterviewDTO {

    private Integer interviewId;

    // Use the correct name to match the Candidate field
    private Integer empId;
    private Long cycleId; // Add this

    private LocalDate date;
    private String panel;
    private InterviewStatus status;
    private Feedback feedback;
    private String detailedFeedback;
    private String review;
    private int round;
    private String department;
    private String client;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
