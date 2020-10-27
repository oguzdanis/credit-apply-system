package com.oguzdanis.creditapplysystem.service.impl;

import com.oguzdanis.creditapplysystem.model.CreditResult;
import com.oguzdanis.creditapplysystem.repository.CreditResultRepository;
import com.oguzdanis.creditapplysystem.service.ICreditResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreditResultServiceImpl implements ICreditResultService {

    @Autowired
    CreditResultRepository creditResultRepository;

    @Override
    public CreditResult findCreditResultByShortCode(String shortCode) {
        return creditResultRepository.findByShortCode(shortCode);
    }

    @Override
    public String findByCreditApplyId(Long creditApplyId) {
        return creditResultRepository.findByCreditApplyId(creditApplyId);
    }
}
