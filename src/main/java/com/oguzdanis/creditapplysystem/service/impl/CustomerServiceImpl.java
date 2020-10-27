package com.oguzdanis.creditapplysystem.service.impl;

import com.oguzdanis.creditapplysystem.constants.Constants;
import com.oguzdanis.creditapplysystem.model.CreditScore;
import com.oguzdanis.creditapplysystem.model.Customer;
import com.oguzdanis.creditapplysystem.repository.CustomerRepository;
import com.oguzdanis.creditapplysystem.service.ICreditScoreService;
import com.oguzdanis.creditapplysystem.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class CustomerServiceImpl implements ICustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ICreditScoreService creditScoreService;

    @Override
    public Customer findCustomerByTckn(String tckn) {
        return customerRepository.findCustomerByTckn(tckn);
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer createCustomer(Customer customer) {
        Boolean isValid = isCustomer(customer);
        if (!isValid)
            return customer;
        this.saveCustomer(customer);
        Integer random = randomCreditScore();
        CreditScore creditScore = buildCreditScore(customer, random);
        creditScoreService.createCreditScore(creditScore);
        return customer;
    }

    private Boolean isCustomer(Customer customer) {
        Customer cust = customerRepository.findCustomerByTckn(customer.getTckn());
        if (cust != null)
            return false;
        return true;
    }

    private CreditScore buildCreditScore(Customer customer, Integer random) {
        CreditScore creditScore = new CreditScore();
        creditScore.setCustomerId(customer.getCustomerId());
        creditScore.setTckn(customer.getTckn());
        creditScore.setValue(random.toString());
        return creditScore;
    }

    private Integer randomCreditScore() {
        SecureRandom secureRandom = new SecureRandom();
        Integer result = secureRandom.nextInt(Constants.ONETHOUSANDNINEHUNDRED);
        return result;
    }
}
