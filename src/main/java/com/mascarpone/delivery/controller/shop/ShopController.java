package com.mascarpone.delivery.controller.shop;

import com.mascarpone.delivery.exception.BadRequestException;
import com.mascarpone.delivery.payload.shop.*;
import com.mascarpone.delivery.security.UserPrincipal;
import com.mascarpone.delivery.service.shop.ShopService;
import com.mascarpone.delivery.service.user.UserService;
import com.mascarpone.delivery.utils.UserAuthChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.mascarpone.delivery.exception.ExceptionConstants.SHOP_NOT_FOUND;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ShopController {
    private final ShopService shopService;
    private final UserService userService;

    /**
     * Getting information about the shop by the shop admin
     *
     * @param userPrincipal - authenticated shop admin
     * @return shop dto
     */
    @GetMapping("/api/shop")
    public ResponseEntity<?> getShop(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        UserAuthChecker.checkAuth(userPrincipal);
        return ResponseEntity.ok(
                new ShopForAdminResponse(
                        userService.getOne(userPrincipal.getId()).getShop()));
    }

    /**
     * Update a shop information
     *
     * @param userPrincipal - authenticated shop admin
     * @param request       - shop dto
     * @return shop entity
     */
    @PutMapping(path = "/api/shop/update",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> updateShop(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                        @RequestBody UpdateShopInfoRequest request) {
        UserAuthChecker.checkAuth(userPrincipal);
        return shopService.updateShopInfo(request, userPrincipal.getId());
    }

    /**
     * Getting information about the shop by the customer
     *
     * @param prefix - shop prefix
     * @return shop dto
     */
    @GetMapping("/api/user/shop/{prefix}")
    public ResponseEntity<?> getShopInfoClient(@PathVariable("prefix") String prefix) {
        return ResponseEntity.ok(
                new ShopForUserResponse(
                        shopService.findByPrefix(prefix)
                                .orElseThrow(() -> new BadRequestException(SHOP_NOT_FOUND))));
    }

    /**
     * Getting the Terms of Service by the customer
     *
     * @param prefix - shop prefix
     * @return Terms of Service
     */
    @GetMapping("/api/user/shop/terms/service/{prefix}")
    public ResponseEntity<?> getServiceTerms(@PathVariable("prefix") String prefix) {
        return ResponseEntity.ok(
                new ShopServiceTermsResponse(
                        shopService.findByPrefix(prefix)
                                .orElseThrow(() -> new BadRequestException(SHOP_NOT_FOUND))));
    }

    /**
     * Getting Delivery terms by the customer
     *
     * @param prefix - shop prefix
     * @return Delivery terms
     */
    @GetMapping("/api/user/shop/terms/delivery/{prefix}")
    public ResponseEntity<?> getDeliveryTerms(@PathVariable("prefix") String prefix) {
        return ResponseEntity.ok(
                new ShopDeliveryTermsResponse(
                        shopService.findByPrefix(prefix)
                                .orElseThrow(() -> new BadRequestException(SHOP_NOT_FOUND))));
    }
}
