package com.mascarpone.delivery.service.auth;

import com.mascarpone.delivery.payload.GeneralAnswer;
import com.mascarpone.delivery.payload.auth.*;
import com.mascarpone.delivery.payload.user.UserLoginPasswordRequest;
import org.springframework.http.ResponseEntity;

import javax.mail.MessagingException;

public interface AuthService {
    GeneralAnswer<String> registerRootAdmin(UserLoginPasswordRequest request);

    ResponseEntity<?> authenticateShop(LoginRequest request);

    ResponseEntity<?> registerShop(SignUpRequest request) throws MessagingException;

    ResponseEntity<?> sendPasswordResetRequest(String email) throws MessagingException;

    GeneralAnswer<String> requestSmsCode(String phoneNumber, String shopPrefix);

    ResponseEntity<?> checkSmsCode(CheckSmsCodeRequest request);

    ResponseEntity<?> registerCourier(CourierRegisterRequest request, Long shopAdminId);

    ResponseEntity<?> authenticateCourier(CourierAndCookLoginRequest request);

    ResponseEntity<?> registerCook(CookRegisterRequest request, Long shopAdminId);

    ResponseEntity<?> authenticateCook(CourierAndCookLoginRequest request);
}
