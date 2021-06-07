package com.mascarpone.delivery.payload.statistics;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrdersStatisticsByDaysListResponse {
    private Long totalOrdersCount;
    private Double totalOrdersValue;
    private Double averageCheck;
    private List<OrdersStatisticsByDaysResponse> days;

    public OrdersStatisticsByDaysListResponse(Long totalOrdersCount, Double totalOrdersValue, Double averageCheck, List<OrdersStatisticsByDaysResponse> days) {
        this.totalOrdersCount = totalOrdersCount;
        this.totalOrdersValue = totalOrdersValue;
        this.averageCheck = averageCheck;
        this.days = days;
    }
}
