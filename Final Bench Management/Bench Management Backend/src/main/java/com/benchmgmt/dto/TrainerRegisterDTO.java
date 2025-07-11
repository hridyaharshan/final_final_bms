package com.benchmgmt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainerRegisterDTO {
    private Integer empId;
    private String name;
    private String email;
    private String password;
}
