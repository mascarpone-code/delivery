package com.mascarpone.delivery.payload.ordermodifier;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderModifierRequest {
    private Long modifierId;
    private Integer modifierCount;
    private String name;
    private String description;
    private Double price;
    private Double proteins;
    private Double fats;
    private Double carbohydrates;
    private Double kiloCalories;
    private Double weight;
}
