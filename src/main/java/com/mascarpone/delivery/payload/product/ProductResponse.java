package com.mascarpone.delivery.payload.product;

import com.mascarpone.delivery.entity.modifier.Modifier;
import com.mascarpone.delivery.entity.product.Product;
import com.mascarpone.delivery.entity.productgroup.ProductGroup;
import com.mascarpone.delivery.entity.productphoto.ProductPhoto;
import com.mascarpone.delivery.entity.unit.Unit;
import com.mascarpone.delivery.payload.modifier.ModifierResponse;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Double proteins;
    private Double fats;
    private Double carbohydrates;
    private Double kiloCalories;
    private Double weight;
    private ProductGroup productGroup;
    private Set<ProductPhoto> productPhotos;
    private List<ModifierResponse> modifiers;
    private Unit unit;
    private boolean isActive;

    public ProductResponse(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.proteins = product.getProteins();
        this.fats = product.getFats();
        this.carbohydrates = product.getCarbohydrates();
        this.kiloCalories = product.getKiloCalories();
        this.weight = product.getWeight();
        this.productGroup = product.getProductGroup();
        this.productPhotos = product.getPhotos();

        List<ModifierResponse> modifierResponses = new ArrayList<>();
        List<Modifier> modifiers = product.getModifiers();

        for (Modifier modifier : modifiers) {
            modifierResponses.add(new ModifierResponse(modifier));
        }

        this.modifiers = modifierResponses;

        if (product.getUnit() != null) {
            this.unit = product.getUnit();
        } else {
            this.unit = null;
        }

        this.isActive = product.isActive();
    }
}
