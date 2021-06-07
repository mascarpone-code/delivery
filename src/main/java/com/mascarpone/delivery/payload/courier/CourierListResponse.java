package com.mascarpone.delivery.payload.courier;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CourierListResponse {
    private long totalCourierCount;
    private List<CourierInAdminListResponse> couriers;

    public CourierListResponse(long totalCourierCount, List<CourierInAdminListResponse> couriers) {
        this.totalCourierCount = totalCourierCount;
        this.couriers = couriers;
    }
}
