package com.mascarpone.delivery.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum PaymentStatus {
    NEW("new"),
    CANCEL("cancel"),
    CONFIRMED("confirmed"),
    REJECTED("rejected");

    private String type;

    @JsonCreator
    public static PaymentStatus fromValue(String value) {
        for (PaymentStatus type : values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        return null;
    }

    @JsonValue
    public String getType() {
        return type;
    }
}
