package com.mascarpone.delivery.controller.auth;

import com.mascarpone.delivery.payload.GeneralAnswer;
import com.mascarpone.delivery.payload.auth.*;
import com.mascarpone.delivery.payload.user.UserLoginPasswordRequest;
import com.mascarpone.delivery.security.UserPrincipal;
import com.mascarpone.delivery.service.auth.AuthService;
import com.mascarpone.delivery.utils.UserAuthChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthController {
    private final AuthService authService;

    /**
     * Root admin registration
     *
     * @param request - login, password and secret word
     * @return
     */
    @PostMapping("/api/root/reg")
    public GeneralAnswer<String> registerRootAdmin(@RequestBody UserLoginPasswordRequest request) {
        return authService.registerRootAdmin(request);
    }

    /**
     * Root admin and shop authentication
     *
     * @param request - login and password
     * @return authentication token and user role
     */
    @PostMapping("/api/shop/auth/login")
    public ResponseEntity<?> authenticateShop(@RequestBody LoginRequest request) {
        return authService.authenticateShop(request);
    }

    /**
     * Shop registration
     *
     * @param userPrincipal - authenticated root admin
     * @param signUpRequest - login, shop name, shop prefix and payment bank
     * @return shop name and shop prefix
     * @throws MessagingException
     */
    @PostMapping("/api/root/regshop")
    public ResponseEntity<?> registerShop(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                          @RequestBody SignUpRequest signUpRequest) throws MessagingException {
        UserAuthChecker.checkAuth(userPrincipal);
        return authService.registerShop(signUpRequest);
    }

    /**
     * Shop's request for password recovery
     *
     * @param email - shop e-mail
     * @return shop name and shop prefix
     */
    @PutMapping("/api/shop/auth/resetpassword/{email}")
    public ResponseEntity<?> sendPasswordResetRequest(@PathVariable String email) throws MessagingException {
        return authService.sendPasswordResetRequest(email);
    }

    /**
     * User authentication: sms code request
     *
     * @param request - user's phone number (79XXXXXXXXX) and shop prefix
     * @return status: registration or authorization
     */
    @PostMapping("api/user/auth/sms")
    public GeneralAnswer<String> getSmsCode(@RequestBody GetSmsCodeRequest request) {
        return authService.requestSmsCode(request.getPhoneNumber(), request.getShopPrefix());
    }

    /**
     * User authentication: sms code check
     *
     * @param request - user's phone number (79XXXXXXXXX), sms code and shop prefix
     * @return authentication token and user role
     */
    @PutMapping("api/user/auth/sms")
    public ResponseEntity<?> sendSmsCode(@RequestBody CheckSmsCodeRequest request) {
        return authService.checkSmsCode(request);
    }

    /**
     * Courier registration
     *
     * @param userPrincipal - authorized shop admin
     * @param request       - courier's login, password, name, phone number and passport
     * @return courier entity
     */
    @PostMapping("/api/shop/courier")
    public ResponseEntity<?> registerCourier(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                             @RequestBody CourierRegisterRequest request) {
        UserAuthChecker.checkAuth(userPrincipal);
        return authService.registerCourier(request, userPrincipal.getId());
    }

    /**
     * Courier authentication
     *
     * @param request - courier's login and password and shop prefix
     * @return authentication token, user role and username
     */
    @PostMapping("/api/courier/login")
    public ResponseEntity<?> authenticateCourier(@RequestBody CourierAndCookLoginRequest request) {
        return authService.authenticateCourier(request);
    }

    /**
     * Cook registration
     *
     * @param userPrincipal - authorized shop admin
     * @param request       - cook's login and password and shop branch id
     * @return cook entity
     */
    @PostMapping("/api/shop/cook")
    public ResponseEntity<?> registerCook(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                          @RequestBody CookRegisterRequest request) {
        UserAuthChecker.checkAuth(userPrincipal);
        return authService.registerCook(request, userPrincipal.getId());
    }

    /**
     * Cook authentication
     *
     * @param request - cook's login and password and shop prefix
     * @return authentication token, user role and username
     */
    @PostMapping("/api/cook/login")
    public ResponseEntity<?> authenticateCook(@RequestBody CourierAndCookLoginRequest request) {
        return authService.authenticateCook(request);
    }
}
