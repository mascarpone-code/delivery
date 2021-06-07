package com.mascarpone.delivery.payload.modifier;

import com.mascarpone.delivery.entity.modifier.Modifier;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ModifierClientResponse {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Double proteins;
    private Double fats;
    private Double carbohydrates;
    private Double kiloCalories;
    private Double weight;
    private String unit;

    public ModifierClientResponse(Modifier modifier) {
        this.id = modifier.getId();
        this.name = modifier.getName();
        this.description = modifier.getDescription();
        this.price = modifier.getPrice();
        this.proteins = modifier.getProteins();
        this.fats = modifier.getFats();
        this.carbohydrates = modifier.getCarbohydrates();
        this.kiloCalories = modifier.getKiloCalories();
        this.weight = modifier.getWeight();
        this.unit = modifier.getUnit().getName();
    }
}
