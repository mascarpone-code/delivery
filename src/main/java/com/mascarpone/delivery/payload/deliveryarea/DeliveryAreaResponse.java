package com.mascarpone.delivery.payload.deliveryarea;

import com.mascarpone.delivery.entity.deliveryarea.DeliveryArea;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveryAreaResponse {
    private Long id;
    private String name;

    public DeliveryAreaResponse(DeliveryArea deliveryArea) {
        id = deliveryArea.getId();
        name = deliveryArea.getName();
    }
}
