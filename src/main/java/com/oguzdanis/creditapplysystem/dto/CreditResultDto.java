package com.oguzdanis.creditapplysystem.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreditResultDto {
    private CreditScoreResult creditScoreResult;
    private String creditResult;
    private Integer CreditResultValue;
}
