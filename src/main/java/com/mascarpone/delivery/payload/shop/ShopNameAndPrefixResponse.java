package com.mascarpone.delivery.payload.shop;

import com.mascarpone.delivery.entity.shop.Shop;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopNameAndPrefixResponse {
    private String shopName;
    private String shopPrefix;

    public ShopNameAndPrefixResponse(Shop shop) {
        this.shopName = shop.getName();
        this.shopPrefix = shop.getPrefix();
    }
}
