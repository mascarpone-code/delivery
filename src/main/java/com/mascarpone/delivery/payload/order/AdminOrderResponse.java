package com.mascarpone.delivery.payload.order;

import com.mascarpone.delivery.entity.enums.OrderStatus;
import com.mascarpone.delivery.entity.enums.OrderType;
import com.mascarpone.delivery.entity.enums.UserOrderType;
import com.mascarpone.delivery.entity.order.UserOrder;
import com.mascarpone.delivery.entity.orderaccessory.OrderAccessory;
import com.mascarpone.delivery.entity.orderproduct.OrderProduct;
import com.mascarpone.delivery.payload.accessory.OrderAccessoryResponse;
import com.mascarpone.delivery.payload.courier.CourierInAdminListResponse;
import com.mascarpone.delivery.payload.product.AdminProductCountResponse;
import com.mascarpone.delivery.payload.shopbranch.ShopBranchResponse;
import com.mascarpone.delivery.payload.user.UserNameAddressResponse;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AdminOrderResponse {
    private Long orderId;
    private ShopBranchResponse shopBranch;
    private Long dateCreate;
    private String UUID;
    private Long UUIDIos;
    private Long orderNumber;
    private UserNameAddressResponse customer;
    private String address;
    private String userNote;
    private String adminNote;
    private BigDecimal fullPrice;
    private BigDecimal paidByBonus;
    private BigDecimal price;
    private Double proteins;
    private Double fats;
    private Double carbohydrates;
    private Double kiloCalories;
    private Double weight;
    private OrderType type;
    private OrderStatus status;
    private boolean isPaid;
    private List<AdminProductCountResponse> products;
    private CourierInAdminListResponse courier;
    //private CookFullResponse cook;
    private UserOrderType userOrderType;
    private String orderTime;
    private String payType;
    private List<OrderAccessoryResponse> orderAccessories;


    public AdminOrderResponse(ShopBranchResponse response, UserOrder order) {
        orderId = order.getId();
        shopBranch = response;
        dateCreate = order.getDateCreate().getTime();
        UUID = order.getUUID();
        UUIDIos = order.getUUIDIos();
        orderNumber = order.getOrderNumber();
        customer = new UserNameAddressResponse(order.getCreator());
        address = order.getAddress();
        userNote = order.getUserNote();
        adminNote = order.getAdminNote();
        fullPrice = order.getFullPrice();
        paidByBonus = order.getPaidByBonus();
        price = order.getPrice();
        proteins = order.getProteins();
        fats = order.getFats();
        carbohydrates = order.getCarbohydrates();
        kiloCalories = order.getKiloCalories();
        weight = order.getWeight();
        type = order.getType();
        status = order.getStatus();
        isPaid = order.isPaid();

        if (order.getUserOrderType() != null) {
            userOrderType = order.getUserOrderType();
        }

        List<OrderProduct> orderProducts = order.getOrderProducts();
        List<AdminProductCountResponse> adminProductResponses = new ArrayList<>();

        for (OrderProduct productOrder : orderProducts) {
            adminProductResponses.add(new AdminProductCountResponse(productOrder));
        }

        products = adminProductResponses;

        if (order.getCourier() != null) {
            courier = new CourierInAdminListResponse(order.getCourier());
        }

        orderTime = order.getOrderTime();

        if (order.getPayType() != null) {
            payType = order.getPayType().getPayType();
        }

        List<OrderAccessory> orderAccessories = order.getOrderAccessories();
        List<OrderAccessoryResponse> orderAccessoryResponses = new ArrayList<>();

        for (OrderAccessory orderAccessory : orderAccessories) {
            if (orderAccessory.getCount() != 0) {
                orderAccessoryResponses.add(new OrderAccessoryResponse(orderAccessory.getAccessoryId(), orderAccessory.getName(), orderAccessory.getCount()));
            }
        }

        this.orderAccessories = orderAccessoryResponses;

//        if (order.getCook() != null) {
//            cook = new CookFullResponse(order.getCook());
//        }
    }
}
