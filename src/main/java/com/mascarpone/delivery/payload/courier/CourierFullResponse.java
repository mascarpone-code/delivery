package com.mascarpone.delivery.payload.courier;

import com.mascarpone.delivery.entity.user.User;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CourierFullResponse {
    private UUID courierId;
    private String login;
    private String name;
    private Long dateCreate;
    private boolean enabled;
    private String passport;
    private boolean active;
    private String phoneNumber;

    public CourierFullResponse(User courier) {
        this.courierId = courier.getUuid();
        this.login = courier.getLogin();
        this.name = courier.getName();
        this.dateCreate = courier.getDateCreate().getTime();
        this.enabled = courier.getEnabled();
        this.passport = courier.getPassport();
        active = courier.getCourierActive();
        phoneNumber = courier.getPhoneNumber();
    }
}
