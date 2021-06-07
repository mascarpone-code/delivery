package com.mascarpone.delivery.payload.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CookRegisterRequest {
    private String login;
    private String password;
    private Long shopBranchId;
}
