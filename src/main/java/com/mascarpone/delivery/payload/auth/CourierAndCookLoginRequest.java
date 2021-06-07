package com.mascarpone.delivery.payload.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourierAndCookLoginRequest {
    private String login;
    private String password;
    private String prefix;
}
