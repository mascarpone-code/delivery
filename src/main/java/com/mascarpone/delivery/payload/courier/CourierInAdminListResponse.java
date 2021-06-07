package com.mascarpone.delivery.payload.courier;

import com.mascarpone.delivery.entity.user.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourierInAdminListResponse {
    private Long courierId;
    private String name;
    private boolean active;

    public CourierInAdminListResponse(User courier) {
        courierId = courier.getId();
        name = courier.getName();
        active = courier.getCourierActive();
    }
}
