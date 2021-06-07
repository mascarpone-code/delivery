package com.mascarpone.delivery.payload.modifier;

import com.mascarpone.delivery.entity.ordermodifier.OrderModifier;
import com.mascarpone.delivery.entity.unit.Unit;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AdminModifierCountResponse {
    private Long modifierId;
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

    public AdminModifierCountResponse(OrderModifier orderModifier) {
        modifierId = orderModifier.getModifierId();
        name = orderModifier.getName();
        description = orderModifier.getDescription();
        price = orderModifier.getPrice();
        proteins = orderModifier.getProteins();
        fats = orderModifier.getFats();
        carbohydrates = orderModifier.getCarbohydrates();
        kiloCalories = orderModifier.getKiloCalories();
        weight = orderModifier.getWeight();

        if (orderModifier.getUnit() != null) {
            this.unit = orderModifier.getUnit();
        } else {
            this.unit = null;
        }

        this.count = orderModifier.getCount();
    }
}
