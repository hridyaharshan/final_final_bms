package com.benchmgmt.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pre_approved_email")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PreApprovedEmail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;
}

