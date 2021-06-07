package com.mascarpone.delivery.payload.order;

import com.mascarpone.delivery.entity.enums.OrderType;
import com.mascarpone.delivery.entity.orderaccessory.OrderAccessory;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RepeatOrderRequest {
    private Long orderId;
    private String deliveryAddress;
    private OrderType orderType;
    private Long shopBranchId;
    private String userNote;
    private double paidByBonus;
    private Long deliveryAreaId;
    private String payType;
    private String orderTime;
    private List<OrderAccessory> orderAccessories;
}
