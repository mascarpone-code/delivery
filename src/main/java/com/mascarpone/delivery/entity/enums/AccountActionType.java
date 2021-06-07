package com.mascarpone.delivery.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum AccountActionType {
    AUTH("auth"),
    NEWORDER("neworder"),
    ORDERFORMED("orderformed"),
    ORDERPREPARING("preparing"),
    ORDERREADY("orderready"),
    ORDERDELIVERING("delivering"),
    ORDERDELIVERED("orderdelivered"),
    ORDERCANCELED("ordercanceled"),
    ORDERACCEPTED("orderaccepted"),
    ORDERDECLINED("orderdeclined"),
    ORDERPAID("orderpaid"),
    FLAMPMESSAGE("flampmessage");

    private final String accountAction;

    @JsonCreator
    public static AccountActionType fromValue(String value) {
        for (AccountActionType actionType : values()) {
            if (actionType.name().equalsIgnoreCase(value)) {
                return actionType;
            }
        }
        return null;
    }

    @JsonValue
    public String getAccountAction() {
        return accountAction;
    }
}
