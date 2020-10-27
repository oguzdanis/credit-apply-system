package com.oguzdanis.creditapplysystem.service.impl;

import com.oguzdanis.creditapplysystem.model.GeneralParm;
import com.oguzdanis.creditapplysystem.repository.GeneralParmRepository;
import com.oguzdanis.creditapplysystem.service.IGeneralParmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GeneralParmServiceImpl implements IGeneralParmService {

    @Autowired
    GeneralParmRepository generalParmRepository;

    @Override
    public GeneralParm findByShortCode(String shortCode) {
        return generalParmRepository.findByShortCodeAndIsActive(shortCode, true);
    }
}
