package com.mascarpone.delivery.service.userpromocode;

import com.mascarpone.delivery.entity.promocode.PromoCode;
import com.mascarpone.delivery.entity.user.User;
import com.mascarpone.delivery.entity.userpromocode.UserPromoCode;
import com.mascarpone.delivery.service.GeneralService;

public interface UserPromoCodeService extends GeneralService<UserPromoCode> {
    Boolean existsByCustomerAndPromoCode(User customer, PromoCode promoCode);
}
