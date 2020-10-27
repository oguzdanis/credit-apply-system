package com.oguzdanis.creditapplysystem.repository;

import com.oguzdanis.creditapplysystem.model.SmsTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SmsTemplateRepository extends JpaRepository<SmsTemplate, Long> {
    SmsTemplate findSmsTemplateByShortCode(String shortCode);
}
