package com.mascarpone.delivery.payload.statistics;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrdersStatisticsByDaysResponse {
    private String dayOfWeek;
    private Integer ordersCount;
    private Double ordersValue;

    public OrdersStatisticsByDaysResponse(String dayOfWeek, Integer ordersCount, Double ordersValue) {
        this.dayOfWeek = dayOfWeek;
        this.ordersCount = ordersCount;
        this.ordersValue = ordersValue;
    }
}
