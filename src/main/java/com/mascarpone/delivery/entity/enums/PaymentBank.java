package com.mascarpone.delivery.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentBank {
    TINKOFF("tinkoff"),
    ALFA("alfa");

    private final String paymentType;
}
