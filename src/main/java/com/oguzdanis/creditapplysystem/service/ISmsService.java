package com.oguzdanis.creditapplysystem.service;

import com.oguzdanis.creditapplysystem.model.Sms;

public interface ISmsService {

    Sms createSms(Sms sms);

    String smsValue(Long creditApplyId);

    Sms sendSms(Long creditApplyId, Long customerId);
}
