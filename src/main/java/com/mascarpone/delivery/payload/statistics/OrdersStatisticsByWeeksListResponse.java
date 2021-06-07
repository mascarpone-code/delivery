package com.mascarpone.delivery.payload.statistics;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrdersStatisticsByWeeksListResponse {
    private Long totalOrdersCount;
    private Double totalOrdersValue;
    private Double averageCheck;
    private List<OrdersStatisticsByWeeksResponse> weeks;

    public OrdersStatisticsByWeeksListResponse(Long totalOrdersCount, Double totalOrdersValue, Double averageCheck, List<OrdersStatisticsByWeeksResponse> weeks) {
        this.totalOrdersCount = totalOrdersCount;
        this.totalOrdersValue = totalOrdersValue;
        this.averageCheck = averageCheck;
        this.weeks = weeks;
    }
}
