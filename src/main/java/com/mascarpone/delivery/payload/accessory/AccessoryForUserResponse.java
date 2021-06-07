package com.mascarpone.delivery.payload.accessory;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccessoryForUserResponse {
    private Long accessoryId;
    private String name;

    public AccessoryForUserResponse(Long accessoryId, String name) {
        this.accessoryId = accessoryId;
        this.name = name;
    }
}
