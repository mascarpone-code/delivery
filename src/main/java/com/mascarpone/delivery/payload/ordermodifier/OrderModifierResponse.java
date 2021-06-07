package com.mascarpone.delivery.payload.ordermodifier;

import com.mascarpone.delivery.entity.ordermodifier.OrderModifier;
import com.mascarpone.delivery.entity.unit.Unit;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderModifierResponse {
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
    private Integer modifierCount;

    public OrderModifierResponse(OrderModifier orderModifier) {
        this.id = orderModifier.getModifierId();
        this.name = orderModifier.getName();
        this.description = orderModifier.getDescription();
        this.price = orderModifier.getPrice();
        this.proteins = orderModifier.getProteins();
        this.fats = orderModifier.getFats();
        this.carbohydrates = orderModifier.getCarbohydrates();
        this.kiloCalories = orderModifier.getKiloCalories();
        this.weight = orderModifier.getWeight();

        if (orderModifier.getUnit() != null) {
            this.unit = orderModifier.getUnit();
        } else {
            this.unit = null;
        }

        modifierCount = orderModifier.getCount();
    }
}
