package com.mascarpone.delivery.payload.product;

import com.mascarpone.delivery.entity.product.Product;
import com.mascarpone.delivery.entity.unit.Unit;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AdminProductResponse {
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

    public AdminProductResponse(Product product) {
        this.productId = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.proteins = product.getProteins();
        this.fats = product.getFats();
        this.carbohydrates = product.getCarbohydrates();
        this.kiloCalories = product.getKiloCalories();
        this.weight = product.getWeight();

        if (product.getUnit() != null) {
            this.unit = product.getUnit();
        } else {
            this.unit = null;
        }
    }
}
