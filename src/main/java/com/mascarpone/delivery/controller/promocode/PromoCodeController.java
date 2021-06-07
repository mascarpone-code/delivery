package com.mascarpone.delivery.controller.promocode;

import com.mascarpone.delivery.entity.promocode.PromoCode;
import com.mascarpone.delivery.security.UserPrincipal;
import com.mascarpone.delivery.service.promocode.PromoCodeService;
import com.mascarpone.delivery.utils.UserAuthChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PromoCodeController {
    private final PromoCodeService promoCodeService;

    /**
     * Shop creates or updates a promocode.
     *
     * @param userPrincipal - authenticated shop admin
     * @param promoCode     - promocode entity
     * @return promocode dto
     */
    @PostMapping("/api/shop/promo")
    public ResponseEntity<?> createPromoCode(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                             @RequestBody PromoCode promoCode) {
        UserAuthChecker.checkAuth(userPrincipal);
        return promoCodeService.createPromoCode(promoCode, userPrincipal.getId());
    }

    /**
     * Shop gets a list of it's promocodes.
     *
     * @param userPrincipal - authenticated shop admin
     * @param page          - page number
     * @param active        - is promocode active or not
     * @return list of promocodes
     */
    @GetMapping("/api/shop/promo")
    public ResponseEntity<?> getPromoCodes(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                           @RequestParam("page") Optional<Integer> page,
                                           @RequestParam("active") boolean active) {
        UserAuthChecker.checkAuth(userPrincipal);
        return promoCodeService.getPromoCodesByShopAdmin(page, active, userPrincipal.getId());
    }

    /**
     * Shop gets it's promocode.
     *
     * @param userPrincipal - authenticated shop admin
     * @param id            - promo code id
     * @return promocode dto
     */
    @GetMapping("/api/shop/promo/{id}")
    public ResponseEntity<?> getPromoCode(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                          @PathVariable("id") Long id) {
        UserAuthChecker.checkAuth(userPrincipal);
        return promoCodeService.getPromoCode(id, userPrincipal.getId());
    }

    /**
     * Customer applies a promocode
     *
     * @param userPrincipal - authenticated customer
     * @param promoCode     - promocode string
     * @return status of the customer's bonus account
     */
    @PostMapping("/api/user/promo")
    public ResponseEntity<?> applyPromoCode(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                            @RequestParam("promoCode") String promoCode) {
        UserAuthChecker.checkAuth(userPrincipal);
        return promoCodeService.applyPromoCodeByCustomer(promoCode, userPrincipal.getId());
    }
}
