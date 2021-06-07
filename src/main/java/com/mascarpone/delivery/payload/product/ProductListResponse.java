package com.mascarpone.delivery.payload.product;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductListResponse {
    private long totalProductCount;
    private List<ProductResponse> products;

    public ProductListResponse(List<ProductResponse> products, long count) {
        this.totalProductCount = count;
        this.products = products;
    }
}
