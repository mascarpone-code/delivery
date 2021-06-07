package com.mascarpone.delivery.payload.courier;

import com.mascarpone.delivery.entity.user.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourierActiveResponse {
    private boolean active;

    public CourierActiveResponse(User courier) {
        this.active = courier.getCourierActive();
    }
}
