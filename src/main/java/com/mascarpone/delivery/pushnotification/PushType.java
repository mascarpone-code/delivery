package com.mascarpone.delivery.pushnotification;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PushType {
    APPLE("apple"),
    ANDROID("android");

    private final String pushType;

    @JsonCreator
    public static PushType fromValue(String value) {
        for (var pushType : values()) {
            if (pushType.name().equalsIgnoreCase(value)) {
                return pushType;
            }
        }

        return null;
    }
}
