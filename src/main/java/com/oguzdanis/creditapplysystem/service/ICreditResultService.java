package com.oguzdanis.creditapplysystem.service;

import com.oguzdanis.creditapplysystem.model.CreditResult;

public interface ICreditResultService {

    CreditResult findCreditResultByShortCode(String shortCode);

    String findByCreditApplyId(Long creditApplyId);
}
