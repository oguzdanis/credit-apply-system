package com.oguzdanis.creditapplysystem.service.impl;

import com.oguzdanis.creditapplysystem.model.Sms;
import com.oguzdanis.creditapplysystem.model.SmsTemplate;
import com.oguzdanis.creditapplysystem.repository.SmsRepository;
import com.oguzdanis.creditapplysystem.service.ICreditApplyService;
import com.oguzdanis.creditapplysystem.service.ICreditResultService;
import com.oguzdanis.creditapplysystem.service.ISmsService;
import com.oguzdanis.creditapplysystem.service.ISmsTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SmsServiceImpl implements ISmsService {

    @Autowired
    ISmsTemplateService smsTemplateService;

    @Autowired
    ICreditResultService creditResultService;

    @Autowired
    ICreditApplyService creditApplyService;

    @Autowired
    SmsRepository smsRepository;

    @Override
    public Sms createSms(Sms sms) {
        return smsRepository.save(sms);
    }

    @Override
    public String smsValue(Long creditApplyId) {
        String creditApplyShortCode = creditResultService.findByCreditApplyId(creditApplyId);
        SmsTemplate smsTemplate = smsTemplateService.findSmsTemplate(creditApplyShortCode);
        Integer limit = creditApplyService.findCreditApplyLimitByCreditApplyId(creditApplyId);
        String result = smsValueResponseBuilder(smsTemplate, limit);
        return result;
    }

    @Override
    public Sms sendSms(Long creditApplyId, Long customerId) {
        String smsVal = smsValue(creditApplyId);
        Sms sms = buildSms(smsVal, customerId);
        createSms(sms);
        return sms;
    }

    private Sms buildSms(String smsVal, Long customerId) {
        Sms sms = new Sms();
        sms.setCustomerId(customerId);
        sms.setValue(smsVal);
        sms.setSmsSentDate(new Date());
        return sms;
    }

    private Boolean validateSendSms(Long applyId) {
        return (applyId !=null) ? true : false;
    }

    private String smsValueResponseBuilder(SmsTemplate smsTemplate, Integer limit) {
        String result = smsTemplate.getValue();
        if (smsTemplate.getShortCode().equals("APPROVED")){
            result = result.replace("?", limit.toString());
        }
        return result;
    }
}
