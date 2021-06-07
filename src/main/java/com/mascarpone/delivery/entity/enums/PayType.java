package com.mascarpone.delivery.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PayType {
    TERMINAL("terminal"),
    CASH("cash");

    private final String payType;
}
