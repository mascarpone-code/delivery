package com.mascarpone.delivery.payload.user;

import com.mascarpone.delivery.entity.user.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserForAdminResponse {
    private Long customerId;
    private String login;
    private String name;
    private String address;
    private Long dateCreate;
    private boolean enabled;

    public UserForAdminResponse(User user) {
        customerId = user.getId();
        this.login = user.getLogin();
        this.name = user.getName();
        this.address = user.getAddress();
        this.dateCreate = user.getDateCreate().getTime();
        this.enabled = user.getEnabled();
    }
}
