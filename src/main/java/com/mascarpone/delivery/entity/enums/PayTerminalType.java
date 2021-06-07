package com.mascarpone.delivery.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PayTerminalType {
    TINKOFF_MOBILE("tinkoff_mobile"),
    TINKOFF_WEB("tinkoff_web");

    private final String terminalType;
}
