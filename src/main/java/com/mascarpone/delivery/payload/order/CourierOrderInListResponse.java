package com.mascarpone.delivery.payload.order;

import com.mascarpone.delivery.entity.order.UserOrder;
import com.mascarpone.delivery.entity.shopbranch.ShopBranch;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourierOrderInListResponse {
    private Long orderId;
    private Long orderNumber;
    private String shopAddress;
    private String deliveryAddress;
    private String customerName;
    private String customerPhoneNumber;

    public CourierOrderInListResponse(UserOrder order, ShopBranch branch) {
        this.orderId = order.getId();
        this.orderNumber = order.getOrderNumber();

        if (branch != null) {
            this.shopAddress = branch.getAddress();
        } else {
            shopAddress = "";
        }

        this.deliveryAddress = order.getAddress();
        customerName = order.getCreator().getName();
        customerPhoneNumber = order.getCreator().getLogin();
    }
}
