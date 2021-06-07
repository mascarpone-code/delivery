package com.mascarpone.delivery.payload.shop;

import com.mascarpone.delivery.entity.shop.Shop;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopServiceTermsResponse {
    private String terms;

    public ShopServiceTermsResponse(Shop shop) {
        this.terms = shop.getServiceTerms();
    }
}
