package com.mascarpone.delivery.payload.order;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AdminOrderListResponse {
    private long totalOrderCount;
    private List<AdminOrderResponse> orders;

    public AdminOrderListResponse(List<AdminOrderResponse> orders, long totalOrderCount) {
        this.totalOrderCount = totalOrderCount;
        this.orders = orders;
    }
}
