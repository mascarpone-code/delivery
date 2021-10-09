package com.mascarpone.delivery.service.auth;

import com.mascarpone.delivery.payload.GeneralAnswer;
import com.mascarpone.delivery.payload.auth.*;
import com.mascarpone.delivery.payload.user.UserLoginPasswordRequest;
import org.springframework.http.ResponseEntity;

import javax.mail.MessagingException;

public interface AuthService {
    /**
     * Root admin registration
     *
     * @param request - login, password and secret word
     * @return
     */
    GeneralAnswer<String> registerRootAdmin(UserLoginPasswordRequest request);

    /**
     * Root admin and shop authentication
     *
     * @param request - login and password
     * @return authentication token
     */
    ResponseEntity<?> authenticateShop(LoginRequest request);

    /**
     * Shop registration
     *
     * @param request - login, shop name, shop prefix and payment bank
     * @return shop name and shop prefix
     * @throws MessagingException
     */
    ResponseEntity<?> registerShop(SignUpRequest request) throws MessagingException;

    /**
     * Shop's request for password recovery
     *
     * @param email - shop e-mail
     * @return shop name and shop prefix
     * @throws MessagingException
     */
    ResponseEntity<?> sendPasswordResetRequest(String email) throws MessagingException;

    /**
     * User authentication: sms code request
     *
     * @param phoneNumber - phone number
     * @param shopPrefix  - shop prefix
     * @return status: registration or authentication
     */
    GeneralAnswer<String> requestSmsCode(String phoneNumber, String shopPrefix);

    /**
     * User authentication: sms code check
     *
     * @param request - user's phone number (79XXXXXXXXX), sms code and shop prefix
     * @return authentication token and user role
     */
    ResponseEntity<?> checkSmsCode(CheckSmsCodeRequest request);

    /**
     * Courier registration
     *
     * @param request     - courier's login, password, name, phone number and passport
     * @param shopAdminId - shop admin id
     * @return courier entity
     */
    ResponseEntity<?> registerCourier(CourierRegisterRequest request, Long shopAdminId);

    /**
     * Courier authentication
     *
     * @param request - courier's login and password and shop prefix
     * @return authentication token, user role and username
     */
    ResponseEntity<?> authenticateCourier(CourierAndCookLoginRequest request);

    /**
     * Cook registration
     *
     * @param request     - cook's login and password and shop branch id
     * @param shopAdminId - shop admin id
     * @return cook entity
     */
    ResponseEntity<?> registerCook(CookRegisterRequest request, Long shopAdminId);

    /**
     * Cook authentication
     *
     * @param request - cook's login and password and shop prefix
     * @return authentication token, user role and username
     */
    ResponseEntity<?> authenticateCook(CourierAndCookLoginRequest request);
}
