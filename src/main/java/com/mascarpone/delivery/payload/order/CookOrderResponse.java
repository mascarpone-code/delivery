package com.mascarpone.delivery.payload.order;

import com.mascarpone.delivery.entity.enums.OrderStatus;
import com.mascarpone.delivery.entity.enums.OrderType;
import com.mascarpone.delivery.entity.enums.UserOrderType;
import com.mascarpone.delivery.entity.order.UserOrder;
import com.mascarpone.delivery.entity.orderproduct.OrderProduct;
import com.mascarpone.delivery.payload.product.AdminProductCountResponse;
import com.mascarpone.delivery.payload.user.UserNameAddressResponse;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CookOrderResponse {
    private Long orderId;
    private Long dateCreate;
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
    private UserOrderType userOrderType;
    private Long paymentDate;

    public CookOrderResponse(UserOrder order, Long paymentDate) {
        orderId = order.getId();
        dateCreate = order.getDateCreate().getTime();
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

        List<OrderProduct> productOrders = order.getOrderProducts();
        List<AdminProductCountResponse> adminProductResponses = new ArrayList<>();

        for (OrderProduct productOrder : productOrders) {
            adminProductResponses.add(new AdminProductCountResponse(productOrder));
        }

        products = adminProductResponses;
        this.paymentDate = paymentDate;
    }
}
