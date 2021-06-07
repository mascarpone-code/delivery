package com.mascarpone.delivery.payload.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourierRegisterRequest {
    private String login;
    private String password;
    private String courierName;
    private String phoneNumber;
    private String passport;
}
