package com.mascarpone.delivery.payload.order;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CookOrderListResponse {
    private long totalOrderCount;
    private List<CookOrderResponse> orders;

    public CookOrderListResponse(List<CookOrderResponse> orders, long totalOrderCount) {
        this.totalOrderCount = totalOrderCount;
        this.orders = orders;
    }
}
