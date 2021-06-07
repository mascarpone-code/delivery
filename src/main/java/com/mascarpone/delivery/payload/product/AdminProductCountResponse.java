package com.mascarpone.delivery.payload.product;

import com.mascarpone.delivery.entity.ordermodifier.OrderModifier;
import com.mascarpone.delivery.entity.orderproduct.OrderProduct;
import com.mascarpone.delivery.entity.product.Product;
import com.mascarpone.delivery.entity.unit.Unit;
import com.mascarpone.delivery.payload.modifier.AdminModifierCountResponse;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AdminProductCountResponse {
    private Long productId;
    private String name;
    private String description;
    private BigDecimal price;
    private Double proteins;
    private Double fats;
    private Double carbohydrates;
    private Double kiloCalories;
    private Double weight;
    private Unit unit;

    private int count;
    private List<AdminModifierCountResponse> modifiers;

    public AdminProductCountResponse(OrderProduct orderProduct) {
        this.productId = orderProduct.getProductId();
        this.name = orderProduct.getName();
        this.description = orderProduct.getDescription();
        this.price = orderProduct.getPrice();
        this.proteins = orderProduct.getProteins();
        this.fats = orderProduct.getFats();
        this.carbohydrates = orderProduct.getCarbohydrates();
        this.kiloCalories = orderProduct.getKiloCalories();
        this.weight = orderProduct.getWeight();

        if (orderProduct.getUnit() != null) {
            this.unit = orderProduct.getUnit();
        } else {
            this.unit = null;
        }

        count = orderProduct.getCount();

        List<OrderModifier> orderModifiers = orderProduct.getOrderModifiers();
        List<AdminModifierCountResponse> modifiers = new ArrayList<>();

        if (orderModifiers != null) {
            for (OrderModifier modifierOrder : orderModifiers) {
                modifiers.add(new AdminModifierCountResponse(modifierOrder));
            }
        }

        this.modifiers = modifiers;
    }
}
