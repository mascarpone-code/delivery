package com.mascarpone.delivery.payload.deliveryarea;

import com.mascarpone.delivery.entity.deliveryarea.DeliveryArea;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DeliveryAreaIdNameMinAmountResponse {
    private Long id;
    private String name;
    private BigDecimal minimumOrderAmount;

    public DeliveryAreaIdNameMinAmountResponse(DeliveryArea area) {
        this.id = area.getId();
        this.name = area.getName();

        if (area.getMinimumOrderAmount() != null) {
            this.minimumOrderAmount = area.getMinimumOrderAmount();
        }
    }
}
