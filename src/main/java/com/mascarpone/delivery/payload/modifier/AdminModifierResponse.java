package com.mascarpone.delivery.payload.modifier;

import com.mascarpone.delivery.entity.modifier.Modifier;
import com.mascarpone.delivery.entity.unit.Unit;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AdminModifierResponse {
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

    public AdminModifierResponse(Modifier modifier) {
        modifierId = modifier.getId();
        name = modifier.getName();
        description = modifier.getDescription();
        price = modifier.getPrice();
        proteins = modifier.getProteins();
        fats = modifier.getFats();
        carbohydrates = modifier.getCarbohydrates();
        kiloCalories = modifier.getKiloCalories();
        weight = modifier.getWeight();

        if (modifier.getUnit() != null) {
            this.unit = modifier.getUnit();
        } else {
            this.unit = null;
        }
    }
}

