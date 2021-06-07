package com.mascarpone.delivery.payload.modifier;

import com.mascarpone.delivery.entity.modifier.Modifier;
import com.mascarpone.delivery.entity.unit.Unit;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ModifierResponse {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Double proteins;
    private Double fats;
    private Double carbohydrates;
    private Double kiloCalories;
    private Double weight;
    private Long dateCreate;
    private Long creatorId;
    private Double maxCount;
    private Unit unit;

    public ModifierResponse(Modifier modifier) {
        this.id = modifier.getId();
        this.name = modifier.getName();
        this.description = modifier.getDescription();
        this.price = modifier.getPrice();
        this.proteins = modifier.getProteins();
        this.fats = modifier.getFats();
        this.carbohydrates = modifier.getCarbohydrates();
        this.kiloCalories = modifier.getKiloCalories();
        this.weight = modifier.getWeight();
        this.dateCreate = modifier.getDateCreate().getTime();
        this.creatorId = modifier.getCreator().getId();
        maxCount = modifier.getMaxCount();

        if (modifier.getUnit() != null) {
            this.unit = modifier.getUnit();
        } else {
            this.unit = null;
        }
    }
}
