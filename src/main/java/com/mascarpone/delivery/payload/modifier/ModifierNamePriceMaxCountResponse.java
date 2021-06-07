package com.mascarpone.delivery.payload.modifier;

import com.mascarpone.delivery.entity.modifier.Modifier;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ModifierNamePriceMaxCountResponse {
    private Long id;
    private String name;
    private BigDecimal price;
    private Double maxCount;

    public ModifierNamePriceMaxCountResponse(Modifier modifier) {
        this.id = modifier.getId();
        this.name = modifier.getName();
        this.price = modifier.getPrice();
        this.maxCount = modifier.getMaxCount();
    }
}

