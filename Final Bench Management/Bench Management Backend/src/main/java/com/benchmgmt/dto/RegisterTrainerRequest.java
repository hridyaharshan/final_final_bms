package com.benchmgmt.dto;

import lombok.Data;

@Data
public class RegisterTrainerRequest {
    private Integer empId;
    private String name;
    private String email;
    private String password;
}
