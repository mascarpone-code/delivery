package com.mascarpone.delivery.payload.order;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CourierOrderListResponse {
    private long totalOrderCount;
    private List<CourierOrderInListResponse> orders;

    public CourierOrderListResponse(long totalOrderCount, List<CourierOrderInListResponse> orders) {
        this.totalOrderCount = totalOrderCount;
        this.orders = orders;
    }
}
