package com.mascarpone.delivery.payload.user;

import com.mascarpone.delivery.entity.deliveryaddress.DeliveryAddress;
import com.mascarpone.delivery.entity.user.User;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UserCurrentAddressResponse {
    private String name;
    private DeliveryAddress customerAddress;
    private String phoneNumber;
    private BigDecimal bonuses;

    public UserCurrentAddressResponse(User user, DeliveryAddress address) {
        name = user.getName();
        customerAddress = address;
        phoneNumber = user.getLogin();
        bonuses = user.getBonusAccount().getBonusAmount();
    }
}
