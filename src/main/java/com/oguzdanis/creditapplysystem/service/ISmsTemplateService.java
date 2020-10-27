package com.oguzdanis.creditapplysystem.service;

import com.oguzdanis.creditapplysystem.model.SmsTemplate;

public interface ISmsTemplateService {

    SmsTemplate findSmsTemplate(String shortCode);
}
