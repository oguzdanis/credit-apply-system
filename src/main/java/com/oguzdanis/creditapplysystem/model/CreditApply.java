package com.oguzdanis.creditapplysystem.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "credit_apply")
@Data
public class CreditApply {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "credit_apply_id")
    private Long creditApplyId;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "credit_result_id")
    private Long creditResultId;

    @Column(name = "credit_limit")
    private Integer creditLimit;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "credit_apply_date")
    private Date creditApplyDate;
}
