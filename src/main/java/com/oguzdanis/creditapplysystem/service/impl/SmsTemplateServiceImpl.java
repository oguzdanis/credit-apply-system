package com.oguzdanis.creditapplysystem.service.impl;

import com.oguzdanis.creditapplysystem.model.SmsTemplate;
import com.oguzdanis.creditapplysystem.repository.SmsTemplateRepository;
import com.oguzdanis.creditapplysystem.service.ISmsTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SmsTemplateServiceImpl implements ISmsTemplateService {

    @Autowired
    SmsTemplateRepository smsTemplateRepository;

    @Override
    public SmsTemplate findSmsTemplate(String shortCode) {
        return smsTemplateRepository.findSmsTemplateByShortCode(shortCode);
    }
}
