package com.oguzdanis.creditapplysystem.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "general_parm")
@Data
public class GeneralParm {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "general_parm_id")
    private Long generalParmId;

    @Column(name = "short_code")
    private String shortCode;

    @Column(name = "name")
    private String name;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "value")
    private String value;
}
