package com.mascarpone.delivery.payload.promocode;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PromoCodeListResponse {
    private long totalPromoCodeCount;
    private List<CreatedPromoCodeResponse> promoCodes;

    public PromoCodeListResponse(long totalPromoCodeCount, List<CreatedPromoCodeResponse> promoCodes) {
        this.totalPromoCodeCount = totalPromoCodeCount;
        this.promoCodes = promoCodes;
    }
}
