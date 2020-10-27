package com.oguzdanis.creditapplysystem.repository;

import com.oguzdanis.creditapplysystem.model.GeneralParm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneralParmRepository extends JpaRepository<GeneralParm, Long> {
    GeneralParm findByShortCodeAndIsActive(String shortCode,Boolean actv);
}
