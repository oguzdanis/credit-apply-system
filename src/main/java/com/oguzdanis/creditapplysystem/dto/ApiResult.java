package com.oguzdanis.creditapplysystem.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApiResult<T> {
    private T data;
    private Boolean success;
    private Integer httpStatus;
    private Error error;

    // success result
    public ApiResult(T data, Boolean success) {
        this.success = success;
        this.data = data;
    }

    // error result
    public ApiResult(Error error) {
        this.success = false;
        this.error = error;
    }
}
