package com.mascarpone.delivery.repository.userpromocode;

import com.mascarpone.delivery.entity.promocode.PromoCode;
import com.mascarpone.delivery.entity.user.User;
import com.mascarpone.delivery.entity.userpromocode.UserPromoCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPromoCodeRepository extends JpaRepository<UserPromoCode, Long> {
    Boolean existsByCustomerAndPromoCode(User customer, PromoCode promoCode);
}
