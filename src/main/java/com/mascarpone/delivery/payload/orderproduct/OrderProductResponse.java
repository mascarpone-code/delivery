package com.mascarpone.delivery.payload.orderproduct;

import com.mascarpone.delivery.entity.ordermodifier.OrderModifier;
import com.mascarpone.delivery.entity.orderproduct.OrderProduct;
import com.mascarpone.delivery.entity.unit.Unit;
import com.mascarpone.delivery.payload.modifier.ModifierResponse;
import com.mascarpone.delivery.payload.ordermodifier.OrderModifierResponse;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderProductResponse {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Double proteins;
    private Double fats;
    private Double carbohydrates;
    private Double kiloCalories;
    private Double weight;
    private Unit unit;
    private Integer productCount;
    private List<OrderModifierResponse> modifiers;

    public OrderProductResponse(OrderProduct productOrder) {
        this.id = productOrder.getProductId();
        this.name = productOrder.getName();
        this.description = productOrder.getDescription();
        this.price = productOrder.getPrice();
        this.proteins = productOrder.getProteins();
        this.fats = productOrder.getFats();
        this.carbohydrates = productOrder.getCarbohydrates();
        this.kiloCalories = productOrder.getKiloCalories();
        this.weight = productOrder.getWeight();

        if (productOrder.getUnit() != null) {
            this.unit = productOrder.getUnit();
        } else {
            this.unit = null;
        }

        List<OrderModifier> modifierOrders = productOrder.getOrderModifiers();
        List<OrderModifierResponse> responses = new ArrayList<>();

        for (OrderModifier modifierOrder : modifierOrders) {
            responses.add(new OrderModifierResponse(modifierOrder));
        }

        productCount = productOrder.getCount();
        modifiers = responses;
    }
}
