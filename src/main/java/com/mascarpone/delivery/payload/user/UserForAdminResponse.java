package com.mascarpone.delivery.payload.user;

import com.mascarpone.delivery.entity.user.User;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserForAdminResponse {
    private UUID customerId;
    private String login;
    private String name;
    private String address;
    private Long dateCreate;
    private boolean enabled;

    public UserForAdminResponse(User user) {
        customerId = user.getUuid();
        login = user.getLogin();
        name = user.getName();
        address = user.getAddress();
        dateCreate = user.getDateCreate().getTime();
        enabled = user.getEnabled();
    }
}
