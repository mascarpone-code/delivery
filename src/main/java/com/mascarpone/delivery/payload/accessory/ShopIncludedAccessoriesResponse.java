package com.mascarpone.delivery.payload.accessory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ShopIncludedAccessoriesResponse {
    private Long accessoryId;
    private String name;
    private Boolean included;
}
