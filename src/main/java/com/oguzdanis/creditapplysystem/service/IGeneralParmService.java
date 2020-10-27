package com.oguzdanis.creditapplysystem.service;

import com.oguzdanis.creditapplysystem.model.GeneralParm;

public interface IGeneralParmService {

    GeneralParm findByShortCode(String shortCode);
}
