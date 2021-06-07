package com.mascarpone.delivery.payload.user;

import com.mascarpone.delivery.entity.user.User;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UserNameAddressResponse {
    private String name;
    private String customerAddress;
    private String phoneNumber;
    private BigDecimal bonuses;

    public UserNameAddressResponse(User user) {
        name = user.getName();
        customerAddress = user.getAddress();
        phoneNumber = user.getLogin();
        bonuses = user.getBonusAccount().getBonusAmount();
    }
}
