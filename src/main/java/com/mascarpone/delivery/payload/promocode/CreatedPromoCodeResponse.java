package com.mascarpone.delivery.payload.promocode;

import com.mascarpone.delivery.entity.promocode.PromoCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreatedPromoCodeResponse {
    private Long id;
    private String promoCode;
    private BigDecimal price;
    private Long dateCreate;
    private Long validFrom;
    private Long validTo;

    public CreatedPromoCodeResponse(PromoCode promoCode) {
        this.id = promoCode.getId();
        this.promoCode = promoCode.getPromoCode();
        this.price = promoCode.getPrice();
        this.dateCreate = promoCode.getDateCreate().getTime();
        this.validFrom = promoCode.getValidFrom().getTime();
        this.validTo = promoCode.getValidTo().getTime();
    }
}
