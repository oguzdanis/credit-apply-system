package com.oguzdanis.creditapplysystem.service;


import com.oguzdanis.creditapplysystem.dto.ApiResult;
import com.oguzdanis.creditapplysystem.dto.CreditResultDto;
import com.oguzdanis.creditapplysystem.dto.CreditScoreResult;
import com.oguzdanis.creditapplysystem.dto.KeyTckn;
import com.oguzdanis.creditapplysystem.model.CreditScore;
import com.oguzdanis.creditapplysystem.model.Customer;

public interface ICreditScoreService {

    CreditScore createCreditScore(CreditScore creditScore);

    CreditScore findCreditScoreByTckn(String tckn);

    CreditScoreResult findCreditScoreByTckn(KeyTckn tckn);

    ApiResult<CreditResultDto> creditResult(KeyTckn tckn);

    ApiResult<CreditResultDto> getCreditLimitByCreatingCustomer(Customer customer);
}
