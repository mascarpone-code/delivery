package com.mascarpone.delivery.payload.auth;

import com.mascarpone.delivery.entity.enums.PaymentBank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {
    private String login;
    private String shopName;
    private String prefix;
    private PaymentBank paymentBank;
}
