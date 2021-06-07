package com.mascarpone.delivery.payload.pushtoken;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePushTokenRequest {
    private String token;
    private String device;
}
