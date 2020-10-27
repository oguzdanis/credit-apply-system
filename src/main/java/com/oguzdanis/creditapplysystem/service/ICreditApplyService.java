package com.oguzdanis.creditapplysystem.service;

import com.oguzdanis.creditapplysystem.model.CreditApply;

import java.util.List;

public interface ICreditApplyService {

    CreditApply createCreditApply(CreditApply creditApply);

    List<Long> findCreditApplyIdByCustomerId(Long customerId);

    Integer findCreditApplyLimitByCreditApplyId(Long creditApplyId);
}
