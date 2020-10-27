package com.oguzdanis.creditapplysystem.dto;

import lombok.*;

// builder design pattern
@Builder
@Getter
@Setter
@ToString
public class Error {
    private String code;
    private String message;
}
