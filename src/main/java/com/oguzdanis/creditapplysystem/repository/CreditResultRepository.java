package com.oguzdanis.creditapplysystem.repository;

import com.oguzdanis.creditapplysystem.model.CreditResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditResultRepository extends JpaRepository<CreditResult, Long> {
    CreditResult findByShortCode(String shortCode);

    @Query("select cr.shortCode from CreditResult cr, CreditApply ca " +
            "where cr.creditResultId = ca.creditResultId and ca.creditApplyId = :creditApplyId")
    String findByCreditApplyId(@Param("creditApplyId") Long creditApplyId);
}
