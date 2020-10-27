package com.oguzdanis.creditapplysystem.repository;

import com.oguzdanis.creditapplysystem.model.CreditApply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditApplyRepository extends JpaRepository<CreditApply, Long> {

    CreditApply findCreditApplyByCreditApplyId(Long creditApplyId);

    @Query("select ca.creditApplyId from CreditApply ca where ca.customerId = :customerId order by ca.creditApplyDate desc")
    List<Long> findCreditApplyIdByCustomerId(Long customerId);

    @Query("select ca.creditLimit from CreditApply ca where ca.creditApplyId= :creditApplyId")
    Integer findCreditApplyLimitByCreditApplyId(@Param("creditApplyId") Long creditApplyId);
}
