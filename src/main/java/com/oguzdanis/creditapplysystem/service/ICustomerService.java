package com.oguzdanis.creditapplysystem.service;


import com.oguzdanis.creditapplysystem.model.Customer;

public interface ICustomerService {

    Customer findCustomerByTckn(String tckn);

    Customer saveCustomer(Customer customer);

    Customer createCustomer(Customer customer);
}
