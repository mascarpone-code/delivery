package com.mascarpone.delivery.payload.order;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserOrderListResponse {
    private long totalOrderCount;
    private List<UserOrderResponse> orders;

    public UserOrderListResponse(List<UserOrderResponse> orders, long totalOrderCount) {
        this.totalOrderCount = totalOrderCount;
        this.orders = orders;
    }
}
