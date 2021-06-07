package com.mascarpone.delivery.payload.accessory;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderAccessoryResponse {
    private Long accessoryId;
    private String name;
    private int count;

    public OrderAccessoryResponse(Long accessoryId, String name, int count) {
        this.accessoryId = accessoryId;
        this.name = name;
        this.count = count;
    }
}
