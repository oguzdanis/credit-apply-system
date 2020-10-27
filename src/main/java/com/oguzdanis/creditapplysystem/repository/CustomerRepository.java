package com.oguzdanis.creditapplysystem.repository;

import com.oguzdanis.creditapplysystem.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findCustomerByTckn(String tckn);
}
