package com.mascarpone.delivery.payload.product;

import com.mascarpone.delivery.entity.ordermodifier.OrderModifier;
import com.mascarpone.delivery.entity.orderproduct.OrderProduct;
import com.mascarpone.delivery.entity.product.Product;
import com.mascarpone.delivery.entity.unit.Unit;
import com.mascarpone.delivery.payload.modifier.ModifierForCookResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class ProductForCookResponse {
    private Long productId;
    private String name;
    private Integer count;
    private List<ModifierForCookResponse> modifiers;
    private Unit unit;

    public ProductForCookResponse(OrderProduct productOrder) {
        this.productId = productOrder.getProductId();
        this.name = productOrder.getName();
        this.count = productOrder.getCount();

        List<OrderModifier> modifierOrders = productOrder.getOrderModifiers();
        List<ModifierForCookResponse> modifiers = new ArrayList<>();

        for (OrderModifier modifierOrder : modifierOrders) {
            modifiers.add(new ModifierForCookResponse(modifierOrder));
        }

        this.modifiers = modifiers;

        if (productOrder.getUnit() != null) {
            this.unit = productOrder.getUnit();
        } else {
            this.unit = null;
        }
    }
}
