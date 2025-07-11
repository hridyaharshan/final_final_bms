package com.benchmgmt.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "trainer")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Trainer {

    @Id
    @Column(name = "emp_id")
    private Integer empId;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;
}
