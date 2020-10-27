package com.oguzdanis.creditapplysystem.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "sms_template")
@Data
public class SmsTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "sms_template_id")
    private Long smsTemplateId;
    @Column(name = "name")
    private String name;
    @Column(name = "value")
    private String value;
    @Column(name = "short_code")
    private String shortCode;
}
