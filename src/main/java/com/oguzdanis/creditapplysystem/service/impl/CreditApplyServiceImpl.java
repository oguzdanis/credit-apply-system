package com.oguzdanis.creditapplysystem.service.impl;

import com.oguzdanis.creditapplysystem.model.CreditApply;
import com.oguzdanis.creditapplysystem.repository.CreditApplyRepository;
import com.oguzdanis.creditapplysystem.service.ICreditApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditApplyServiceImpl implements ICreditApplyService {

    @Autowired
    CreditApplyRepository creditApplyRepository;

    @Override
    public CreditApply createCreditApply(CreditApply creditApply) {
        return creditApplyRepository.save(creditApply);
    }

    @Override
    public List<Long> findCreditApplyIdByCustomerId(Long customerId) {
        return creditApplyRepository.findCreditApplyIdByCustomerId(customerId);
    }

    @Override
    public Integer findCreditApplyLimitByCreditApplyId(Long creditApplyId) {
        return creditApplyRepository.findCreditApplyLimitByCreditApplyId(creditApplyId);
    }
}
