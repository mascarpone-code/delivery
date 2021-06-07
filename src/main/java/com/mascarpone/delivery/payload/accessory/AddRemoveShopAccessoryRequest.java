package com.mascarpone.delivery.payload.accessory;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddRemoveShopAccessoryRequest {
    private Long accessoryId;
    private Boolean included;
}
