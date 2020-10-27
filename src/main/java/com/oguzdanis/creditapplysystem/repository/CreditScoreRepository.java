package com.oguzdanis.creditapplysystem.repository;

import com.oguzdanis.creditapplysystem.model.CreditScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditScoreRepository extends JpaRepository<CreditScore, Long> {
    CreditScore findCreditScoreByTckn(String tckn);
}
