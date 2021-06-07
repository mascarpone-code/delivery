package com.mascarpone.delivery.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus {
    FORMED("сформирован"),
    PREPARING("готовится"),
    DELIVERING("доставляется/готов к выдаче"),
    DELIVERED("доставлен/выдан"),
    CANCELED("отменён");

    private final String status;
}
