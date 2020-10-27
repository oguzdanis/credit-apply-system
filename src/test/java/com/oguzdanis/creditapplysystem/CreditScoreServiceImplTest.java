package com.oguzdanis.creditapplysystem;


import com.oguzdanis.creditapplysystem.dto.ApiResult;
import com.oguzdanis.creditapplysystem.dto.CreditResultDto;
import com.oguzdanis.creditapplysystem.dto.KeyTckn;
import com.oguzdanis.creditapplysystem.enumeration.GeneralEnumeration;
import com.oguzdanis.creditapplysystem.model.CreditResult;
import com.oguzdanis.creditapplysystem.model.CreditScore;
import com.oguzdanis.creditapplysystem.model.Customer;
import com.oguzdanis.creditapplysystem.model.GeneralParm;
import com.oguzdanis.creditapplysystem.repository.CreditScoreRepository;
import com.oguzdanis.creditapplysystem.service.impl.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.Silent.class)
public class CreditScoreServiceImplTest {

    @InjectMocks
    private CreditScoreServiceImpl creditScoreService;

    @Mock
    CreditScoreRepository creditScoreRepository;

    @Mock
    CustomerServiceImpl customerService;

    @Mock
    CreditResultServiceImpl creditResultService;

    @Mock
    CreditApplyServiceImpl creditApplyService;

    @Mock
    SmsServiceImpl smsService;

    @Mock
    GeneralParmServiceImpl generalParmService;

    CreditScore creditScore;
    Customer customer;
    CreditResult creditResult;
    List<Long> creditApplyIdList;
    GeneralParm limitMultiplierParm;

    @Before
    public void setUp() {
        initMocks(this);
        creditScore = new CreditScore();
        creditScore.setTckn("12345678912");
        creditScore.setCustomerId(1L);
        creditScore.setCreditScoreId(1L);

        customer = new Customer();
        customer.setCustomerId(1L);
        customer.setTckn("12345678912");

        creditResult = new CreditResult();
        creditResult.setCreditResultId(1L);

        creditApplyIdList = new ArrayList<>();
        creditApplyIdList.add(1L);

        limitMultiplierParm = new GeneralParm();
        limitMultiplierParm.setValue("4");
    }

    @Test
    public void getCreditResultTest_CreditScoreLessThanFiveHundred_UNSUCCESS() {
        String tckn = "12345678912";
        KeyTckn keyTckn = KeyTckn.builder().tckn("12345678912").build();
        creditScore.setValue("400");
        creditResult.setName("Red.");
        creditResult.setShortCode("DECLINE");
        Mockito.when(creditScoreRepository.findCreditScoreByTckn(tckn)).thenReturn(creditScore);
        Mockito.when(customerService.findCustomerByTckn(tckn)).thenReturn(customer);
        Mockito.when(creditResultService.findCreditResultByShortCode("DECLINE")).thenReturn(creditResult);
        Mockito.when(creditApplyService.findCreditApplyIdByCustomerId(customer.getCustomerId())).thenReturn(creditApplyIdList);
        Mockito.when(creditResultService.findByCreditApplyId(1L)).thenReturn("DECLINE");
        ApiResult<CreditResultDto> result = creditScoreService.creditResult(keyTckn);
        Assert.assertEquals(result.getSuccess(), false);
    }

    @Test
    public void getCreditResultTest_CreditScoreGreaterThanFiveHundredLessThenThousand_UNSUCCESS() {
        String tckn = "12345678912";
        KeyTckn keyTckn = KeyTckn.builder().tckn("12345678912").build();
        customer.setSalary(3000);
        creditScore.setValue("600");
        creditResult.setName("Onay");
        creditResult.setShortCode("APPROVED");
        Mockito.when(creditScoreRepository.findCreditScoreByTckn(tckn)).thenReturn(creditScore);
        Mockito.when(customerService.findCustomerByTckn(tckn)).thenReturn(customer);
        Mockito.when(creditResultService.findCreditResultByShortCode("APPROVED")).thenReturn(creditResult);
        Mockito.when(creditApplyService.findCreditApplyIdByCustomerId(customer.getCustomerId())).thenReturn(creditApplyIdList);
        Mockito.when(creditResultService.findByCreditApplyId(1L)).thenReturn("APPROVED");
        ApiResult<CreditResultDto> result = creditScoreService.creditResult(keyTckn);
        Assert.assertEquals(result.getSuccess(), true);
    }

    @Test
    public void getCreditResultTest_CreditScoreGreaterThanThousand_UNSUCCESS() {
        String tckn = "12345678912";
        KeyTckn keyTckn = KeyTckn.builder().tckn("12345678912").build();
        customer.setSalary(3000);
        creditScore.setValue("1200");
        creditResult.setName("Onay");
        creditResult.setShortCode("APPROVED");
        Mockito.when(creditScoreRepository.findCreditScoreByTckn(tckn)).thenReturn(creditScore);
        Mockito.when(customerService.findCustomerByTckn(tckn)).thenReturn(customer);
        Mockito.when(creditResultService.findCreditResultByShortCode("APPROVED")).thenReturn(creditResult);
        Mockito.when(creditApplyService.findCreditApplyIdByCustomerId(customer.getCustomerId())).thenReturn(creditApplyIdList);
        Mockito.when(creditResultService.findByCreditApplyId(1L)).thenReturn("APPROVED");

        Mockito.when(generalParmService.findByShortCode("CREDIT_LIMIT_MULTIPLIER")).thenReturn(limitMultiplierParm) ;
        ApiResult<CreditResultDto> result = creditScoreService.creditResult(keyTckn);
        Integer expectedLimit = Integer.parseInt(limitMultiplierParm.getValue())*customer.getSalary();
        Assert.assertEquals(result.getData().getCreditResultValue(), expectedLimit);
    }

    @Test
    public void isTheTcknElevenDigits_NOK(){
        String tckn = "123";
        KeyTckn keyTckn = KeyTckn.builder().tckn(tckn).build();
        ApiResult<CreditResultDto> result = creditScoreService.creditResult(keyTckn);
        Assert.assertEquals(result.getError().getMessage(), GeneralEnumeration.CreditResult.TCKNISREQUIRED.getMessage());
    }

    @Test
    public void isTheTcknElevenDigits_OK(){
        String tckn = "12345678912";
        KeyTckn keyTckn = KeyTckn.builder().tckn(tckn).build();
        ApiResult<CreditResultDto> result = creditScoreService.creditResult(keyTckn);
        Assert.assertNotEquals(result.getError().getMessage(), GeneralEnumeration.CreditResult.TCKNISREQUIRED.getMessage());
    }
}
