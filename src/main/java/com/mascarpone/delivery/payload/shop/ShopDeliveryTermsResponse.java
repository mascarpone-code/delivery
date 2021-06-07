package com.mascarpone.delivery.payload.shop;

import com.mascarpone.delivery.entity.shop.Shop;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopDeliveryTermsResponse {
    private String terms;

    public ShopDeliveryTermsResponse(Shop shop) {
        this.terms = shop.getDeliveryTerms();
    }
}
