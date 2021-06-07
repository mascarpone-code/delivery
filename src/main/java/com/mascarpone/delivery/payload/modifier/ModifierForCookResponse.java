package com.mascarpone.delivery.payload.modifier;

import com.mascarpone.delivery.entity.ordermodifier.OrderModifier;
import com.mascarpone.delivery.entity.unit.Unit;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ModifierForCookResponse {
    private Long modifierId;
    private String name;
    private Integer count;
    private Unit unit;

    public ModifierForCookResponse(OrderModifier modifierOrder) {
        this.modifierId = modifierOrder.getModifierId();
        this.name = modifierOrder.getName();
        this.count = modifierOrder.getCount();

        if (modifierOrder.getUnit() != null) {
            this.unit = modifierOrder.getUnit();
        } else {
            this.unit = null;
        }
    }
}
