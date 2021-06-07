package com.mascarpone.delivery.payload.user;

import com.mascarpone.delivery.entity.user.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserShopPrefixAndEmailResponse {
    private String prefix;
    private String email;

    public UserShopPrefixAndEmailResponse(User user) {
        this.prefix = user.getShop().getPrefix();
        this.email = user.getLogin();
    }
}
