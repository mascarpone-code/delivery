package com.mascarpone.delivery.payload.statistics;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrdersStatisticsByWeeksResponse {
    private String firstDayOfWeek;
    private String lastDayOfWeek;
    private Integer ordersCount;
    private Double ordersValue;

    public OrdersStatisticsByWeeksResponse(String firstDayOfWeek, String lastDayOfWeek, Integer ordersCount, Double ordersValue) {
        this.firstDayOfWeek = firstDayOfWeek;
        this.lastDayOfWeek = lastDayOfWeek;
        this.ordersCount = ordersCount;
        this.ordersValue = ordersValue;
    }
}
