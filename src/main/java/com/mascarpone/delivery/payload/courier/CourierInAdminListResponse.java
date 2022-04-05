package com.mascarpone.delivery.payload.courier;

import com.mascarpone.delivery.entity.user.User;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CourierInAdminListResponse {
    private UUID courierId;
    private String name;
    private boolean active;

    public CourierInAdminListResponse(User courier) {
        courierId = courier.getUuid();
        name = courier.getName();
        active = courier.getCourierActive();
    }
}
