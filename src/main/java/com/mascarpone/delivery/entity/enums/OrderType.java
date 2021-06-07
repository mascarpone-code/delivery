package com.mascarpone.delivery.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderType {
    DELIVERY("доставка"),
    PICKUP("самовывоз");

    private final String type;
}
