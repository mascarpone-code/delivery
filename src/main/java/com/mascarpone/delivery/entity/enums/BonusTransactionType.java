package com.mascarpone.delivery.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BonusTransactionType {
    ENROLLMENT("зачисление"),
    WRITEOFF("списание");

    private final String type;
}
