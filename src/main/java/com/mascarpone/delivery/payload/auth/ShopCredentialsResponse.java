package com.mascarpone.delivery.payload.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopCredentialsResponse {
    private String login;
    private String password;

    public ShopCredentialsResponse(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
