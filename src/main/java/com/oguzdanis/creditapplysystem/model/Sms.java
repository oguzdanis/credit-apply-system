package com.oguzdanis.creditapplysystem.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "sms")
@Data
public class Sms {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "sms_id")
    private Long smsId;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "value")
    private String value;

    @Column(name = "sms_sent_date")
    private Date smsSentDate;
}
