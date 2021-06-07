package com.mascarpone.delivery.payload.order;

import com.mascarpone.delivery.entity.enums.OrderStatus;
import com.mascarpone.delivery.entity.enums.OrderType;
import com.mascarpone.delivery.entity.order.UserOrder;
import com.mascarpone.delivery.entity.orderaccessory.OrderAccessory;
import com.mascarpone.delivery.entity.orderproduct.OrderProduct;
import com.mascarpone.delivery.payload.accessory.OrderAccessoryResponse;
import com.mascarpone.delivery.payload.product.AdminProductCountResponse;
import com.mascarpone.delivery.payload.user.UserNameAddressResponse;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CreatedOrderResponse {
    private Long orderId;
    private Long dateCreate;
    private String UUID;
    private Long UUIDIos;
    private Long orderNumber;
    private UserNameAddressResponse customer;
    private String address;
    private String userNote;
    private BigDecimal price;
    private Double proteins;
    private Double fats;
    private Double carbohydrates;
    private Double kiloCalories;
    private Double weight;
    private OrderType type;
    private OrderStatus orderStatus;
    private boolean isPaid;
    private List<AdminProductCountResponse> products;
    private String payType;
    private String orderTime;
    private List<OrderAccessoryResponse> orderAccessories;

    public CreatedOrderResponse(UserOrder order) {
        orderId = order.getId();
        dateCreate = order.getDateCreate().getTime();
        UUID = order.getUUID();
        UUIDIos = order.getUUIDIos();
        orderNumber = order.getOrderNumber();
        customer = new UserNameAddressResponse(order.getCreator());
        address = order.getAddress();
        userNote = order.getUserNote();
        price = order.getPrice();
        proteins = order.getProteins();
        fats = order.getFats();
        carbohydrates = order.getCarbohydrates();
        kiloCalories = order.getKiloCalories();
        weight = order.getWeight();
        type = order.getType();
        orderStatus = order.getStatus();
        isPaid = order.isPaid();

        List<OrderProduct> productOrders = order.getOrderProducts();
        List<AdminProductCountResponse> adminProductResponses = new ArrayList<>();

        for (OrderProduct productOrder : productOrders) {
            adminProductResponses.add(new AdminProductCountResponse(productOrder));
        }

        products = adminProductResponses;

        if (order.getPayType() != null) {
            payType = order.getPayType().getPayType();
        }

        orderTime = order.getOrderTime();

        List<OrderAccessory> orderAccessories = order.getOrderAccessories();
        List<OrderAccessoryResponse> orderAccessoryResponses = new ArrayList<>();

        for (OrderAccessory orderAccessory : orderAccessories) {
            orderAccessoryResponses.add(new OrderAccessoryResponse(orderAccessory.getAccessoryId(), orderAccessory.getName(), orderAccessory.getCount()));
        }

        this.orderAccessories = orderAccessoryResponses;
    }
}
