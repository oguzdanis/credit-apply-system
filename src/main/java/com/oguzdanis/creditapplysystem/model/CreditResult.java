package com.oguzdanis.creditapplysystem.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "credit_result")
@Data
public class CreditResult {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "credit_result_id")
    private Long creditResultId;

    @Column(name = "name")
    private String name;

    @Column(name = "short_code")
    private String shortCode;

}
