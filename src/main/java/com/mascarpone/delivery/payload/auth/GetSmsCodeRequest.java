package com.mascarpone.delivery.payload.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetSmsCodeRequest {
    private String phoneNumber;
    private String shopPrefix;
}
