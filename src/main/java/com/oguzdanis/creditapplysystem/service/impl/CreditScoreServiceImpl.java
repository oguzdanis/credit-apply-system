package com.oguzdanis.creditapplysystem.service.impl;

import com.oguzdanis.creditapplysystem.constants.Constants;
import com.oguzdanis.creditapplysystem.enumeration.GeneralEnumeration;
import com.oguzdanis.creditapplysystem.dto.*;
import com.oguzdanis.creditapplysystem.dto.Error;
import com.oguzdanis.creditapplysystem.model.*;
import com.oguzdanis.creditapplysystem.repository.CreditScoreRepository;
import com.oguzdanis.creditapplysystem.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CreditScoreServiceImpl implements ICreditScoreService {

    @Autowired
    CreditScoreRepository creditScoreRepository;

    @Autowired
    ICreditResultService creditResultService;

    @Autowired
    ICustomerService customerService;

    @Autowired
    IGeneralParmService GeneralParmService;

    @Autowired
    ICreditApplyService creditApplyService;

    @Autowired
    ISmsService smsService;

    @Override
    public CreditScore createCreditScore(CreditScore creditScore) {
        return creditScoreRepository.save(creditScore);
    }

    @Override
    public CreditScore findCreditScoreByTckn(String tckn) {
        return creditScoreRepository.findCreditScoreByTckn(tckn);
    }

    @Override
    public CreditScoreResult findCreditScoreByTckn(KeyTckn keyTckn) {
        CreditScoreResult creditScoreResult = new CreditScoreResult();
        String tckn = keyTckn.getTckn();
        CreditScore creditScore = findCreditScoreByTckn(tckn);
        if (creditScore != null && creditScore.getValue() != null) {
            creditScoreResult.setCreditScore(creditScore.getValue());
        }
        return creditScoreResult;
    }

    @Override
    public ApiResult<CreditResultDto> creditResult(KeyTckn tckn) {
        ApiResult<CreditResultDto> result = validateTckn(tckn);
        if (result.getError() != null)
            return result;
        CreditScoreResult creditScore = findCreditScoreByTckn(tckn);
        if (creditScore.getCreditScore() == null) {
            Error error = Error.builder().code("CREDIT_SCORE_NOT_FOUND").message("Kresi skoru yetersiz").build();
            result = new ApiResult<>(error);
            return result;
        }

        int creditScoreInt = Integer.parseInt(creditScore.getCreditScore());
        CreditResultDto creditResultDto = new CreditResultDto();
        // SetCreditScore
        creditResultDto.setCreditScoreResult(creditScore);

        Customer customer = customerService.findCustomerByTckn(tckn.getTckn());
        Long customerId = customer.getCustomerId();

        CreditResult creditResult;
        if (creditScoreInt < 500) {
            creditResult = creditResultService.findCreditResultByShortCode("DECLINE");
            creditResultDto.setCreditResult(creditResult.getName());
            result = buildErrorResult(result, creditResultDto, HttpStatus.BAD_REQUEST, "CREDIT_LIMIT_INSUFFICIENT", "Kredi limiti yetersiz", false);
            createCreditApply(customerId, creditResult.getCreditResultId(), creditResultDto.getCreditResultValue());
            sendSms(customerId);
            return result;
        }

        Integer custSalary = customer.getSalary();
        if (creditScoreInt > Constants.FIVEHUNDRED && creditScoreInt < Constants.THOUSAND
                && custSalary < Constants.FOURTHOUSAND) {
            creditResult = creditResultService.findCreditResultByShortCode("APPROVED");
            creditResultDto.setCreditResult(creditResult.getName());
            creditResultDto.setCreditResultValue(Constants.TENTHOUSAND);
            result = buildSuccessResult(result, creditResultDto, true);
            createCreditApply(customerId, creditResult.getCreditResultId(), creditResultDto.getCreditResultValue());
            sendSms(customerId);
            return result;
        }


        if (creditScoreInt > Constants.FIVEHUNDRED && creditScoreInt < Constants.THOUSAND
                && custSalary > Constants.FOURTHOUSAND) {
            creditResultDto.setCreditResult("Eksik bırakılmış case");
            creditResult = creditResultService.findCreditResultByShortCode("DECLINE");
            creditResultDto.setCreditResult(creditResult.getName());
            result = buildErrorResult(result, creditResultDto, HttpStatus.BAD_REQUEST, "EKSIK_BIRAKILMIS_CASE", "Eksik bırakılmış case", false);
            return result;
        }

        if (creditScoreInt >= Constants.THOUSAND) {
            creditResult = creditResultService.findCreditResultByShortCode("APPROVED");
            creditResultDto.setCreditResult(creditResult.getName());
            Integer creditLimitVal = getCreditLimitValForUpperCreditScore(custSalary);
            creditResultDto.setCreditResultValue(creditLimitVal);
            result = buildSuccessResult(result, creditResultDto, true);
            createCreditApply(customerId, creditResult.getCreditResultId(), creditLimitVal);
            sendSms(customerId);

            return result;
        }

        return result;
    }

    @Override
    public ApiResult<CreditResultDto> getCreditLimitByCreatingCustomer(Customer customerRequest) {
        ApiResult<CreditResultDto> result = new ApiResult<>();
        Customer customer = customerService.createCustomer(customerRequest);
        if (customer != null && customer.getTckn() != null) {
            KeyTckn tckn = KeyTckn.builder().tckn(customer.getTckn()).build();
            result = creditResult(tckn);
        }
        return result;
    }

    private void sendSms(Long customerId) {
        List<Long> creditApplyIdList = creditApplyService.findCreditApplyIdByCustomerId(customerId);
        Long creditApplyId = creditApplyIdList.stream().findFirst().orElse(null);
        smsService.sendSms(creditApplyId, customerId);
    }

    private Integer getCreditLimitValForUpperCreditScore(Integer custSalary) {
        GeneralParm limitMultiplierParm = GeneralParmService.findByShortCode("CREDIT_LIMIT_MULTIPLIER");
        Integer creditLimitMultiplier = Integer.parseInt(limitMultiplierParm.getValue());
        return creditLimitMultiplier * custSalary;
    }

    private ApiResult<CreditResultDto> validateTckn(KeyTckn tckn) {
        //Tckn validation control algorithm has been passed
        ApiResult<CreditResultDto> result = new ApiResult<>();
        if (tckn.getTckn().length() != Constants.TCKNLENGTH) { // checking character number only

            result.setHttpStatus(HttpStatus.BAD_REQUEST.value());
            result.setSuccess(Boolean.FALSE);
            Error error = Error
                    .builder().code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                    .message(GeneralEnumeration.CreditResult.TCKNISREQUIRED.getMessage()).build();
            result.setError(error);
        }
        return result;
    }

    private ApiResult<CreditResultDto> buildSuccessResult(ApiResult<CreditResultDto> result, CreditResultDto creditResultDto, Boolean isSucccess) {
        result = new ApiResult<>(creditResultDto, isSucccess);
        result.setHttpStatus(HttpStatus.OK.value());
        return result;
    }

    private ApiResult<CreditResultDto> buildErrorResult(ApiResult<CreditResultDto> result,
                                                        CreditResultDto creditResultDto, HttpStatus httpStatus, String code, String message, Boolean isSucccess) {
        result = new ApiResult<>(creditResultDto, isSucccess);
        result.setHttpStatus(httpStatus.value());
        Error error = Error.builder().code(code).message(message).build();
        result.setError(error);
        return result;
    }

    private void createCreditApply(Long customerId, Long creditResultId, Integer limit) {
        CreditApply creditApply = new CreditApply();
        creditApply.setCreditResultId(creditResultId);
        creditApply.setCustomerId(customerId);
        creditApply.setCreditLimit(limit);
        creditApply.setCreditApplyDate(new Date());
        creditApplyService.createCreditApply(creditApply);
    }
}
