package com.mascarpone.delivery.controller.user;

import com.mascarpone.delivery.payload.user.UserNameAddressRequest;
import com.mascarpone.delivery.security.UserPrincipal;
import com.mascarpone.delivery.service.user.UserService;
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
public class UserController {
    private final UserService userService;

    /**
     * Search for users by phone number.
     *
     * @param userPrincipal - authenticated shop admin
     * @param phoneNumber   - phone number
     * @param page          - page number
     * @return list of users
     */
    @GetMapping("/api/shop/user")
    public ResponseEntity<?> findUsersByPhoneNumberAndShopPrefix(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                                 @RequestParam("phoneNumber") String phoneNumber,
                                                                 @RequestParam("page") Optional<Integer> page) {
        UserAuthChecker.checkAuth(userPrincipal);
        return userService.findUsersByPhoneNumberAndShopPrefix(phoneNumber, page, userPrincipal.getId());
    }

    /**
     * Customer gets his profile.
     *
     * @param userPrincipal - authenticated customer
     * @return customer's profile
     */
    @GetMapping("/api/user/profile")
    public ResponseEntity<?> getUserProfile(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        UserAuthChecker.checkAuth(userPrincipal);
        return userService.getUserProfile(userPrincipal.getId());
    }

    /**
     * Customer updates his profile.
     *
     * @param userPrincipal - authenticated customer
     * @param request       - customer's name and address
     * @return customer's profile
     */
    @PutMapping("/api/user/profile")
    public ResponseEntity<?> updateCustomerProfile(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                   @RequestBody UserNameAddressRequest request) {
        UserAuthChecker.checkAuth(userPrincipal);
        return userService.updateCustomerProfile(request, userPrincipal.getId());
    }
}
