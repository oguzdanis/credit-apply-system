package com.oguzdanis.creditapplysystem.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "customer")
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "tckn")
    private String tckn;

    @Column(name = "salary")
    private Integer salary;

    @Column(name = "phone_number")
    private String phoneNumber;
}
