package com.mascarpone.delivery.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    ROOT_ADMIN("rootadmin"),
    SHOP("shop"),
    MANAGER("manager"),
    COURIER("courier"),
    USER("user"),
    COOK("cook");

    private final String roleName;
}
