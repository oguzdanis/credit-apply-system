package com.oguzdanis.creditapplysystem.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "credit_score")
@Data
public class CreditScore {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "credit_score_id")
    private Long creditScoreId;

    @Column(name = "tckn")
    private String tckn;

    @Column(name = "value")
    private String value;

    @Column(name = "customer_id")
    private Long customerId;
}
