package com.mascarpone.delivery.payload.promocode;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UserPromoCodeResponse {
    private BigDecimal promoCodeBonusAmount;
    private BigDecimal userBonusAccountAmount;

    public UserPromoCodeResponse(BigDecimal promoCodeBonusAmount, BigDecimal userBonusAccountAmount) {
        this.promoCodeBonusAmount = promoCodeBonusAmount;
        this.userBonusAccountAmount = userBonusAccountAmount;
    }
}
