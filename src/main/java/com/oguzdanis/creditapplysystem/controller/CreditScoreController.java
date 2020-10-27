package com.oguzdanis.creditapplysystem.controller;

import com.oguzdanis.creditapplysystem.dto.*;
import com.oguzdanis.creditapplysystem.model.Customer;
import com.oguzdanis.creditapplysystem.service.ICreditScoreService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/credit/score")
public class CreditScoreController {

    @Autowired
    ICreditScoreService creditScoreService;

    @PostMapping("/createCustomer")
    public ApiResult<CreditResultDto> getCreditLimitByCreatingCustomer(@RequestBody Customer customer){
        return creditScoreService.getCreditLimitByCreatingCustomer(customer);
    }
}
