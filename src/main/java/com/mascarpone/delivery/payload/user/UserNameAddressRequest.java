package com.mascarpone.delivery.payload.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserNameAddressRequest {
    private String name;
    private String address;
}
