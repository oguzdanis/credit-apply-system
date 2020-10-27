package com.oguzdanis.creditapplysystem.repository;

import com.oguzdanis.creditapplysystem.model.Sms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SmsRepository extends JpaRepository<Sms, Long> {

}
