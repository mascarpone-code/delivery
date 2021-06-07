package com.mascarpone.delivery.payload.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckSmsCodeRequest {
    private String phoneNumber;
    private String smsCode;
    private String shopPrefix;
}
