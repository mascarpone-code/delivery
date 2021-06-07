package com.mascarpone.delivery.payload.order;

import com.mascarpone.delivery.entity.order.UserOrder;
import com.mascarpone.delivery.entity.orderproduct.OrderProduct;
import com.mascarpone.delivery.payload.product.ProductForCookResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderForCookResponse {
    private Long orderId;
    private Long orderNumber;
    private List<ProductForCookResponse> products;

    public OrderForCookResponse(UserOrder order) {
        this.orderId = order.getId();
        this.orderNumber = order.getOrderNumber();

        List<OrderProduct> products = order.getOrderProducts();
        List<ProductForCookResponse> productForCookResponses = new ArrayList<>();

        for (OrderProduct productOrder : products) {
            productForCookResponses.add(new ProductForCookResponse(productOrder));
        }

        this.products = productForCookResponses;
    }
}
