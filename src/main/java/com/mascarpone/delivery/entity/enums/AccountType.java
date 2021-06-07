package com.mascarpone.delivery.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AccountType {
    ROOTADMIN("root_admin"),
    CUSTOMER("Покупатель"),
    COURIER("Курьер"),
    SHOP("Магазин"),
    COOK("Повар");

    private final String type;
}
