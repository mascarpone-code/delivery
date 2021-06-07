package com.mascarpone.delivery.controller.pushtoken;

import com.mascarpone.delivery.payload.GeneralAnswer;
import com.mascarpone.delivery.payload.pushtoken.CreatePushTokenRequest;
import com.mascarpone.delivery.security.UserPrincipal;
import com.mascarpone.delivery.service.pushtoken.PushTokenService;
import com.mascarpone.delivery.utils.UserAuthChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PushTokenController {
    private final PushTokenService pushTokenService;

    /**
     * Creating a customer's push token
     *
     * @param userPrincipal - authenticated customer
     * @param request       - push token and type of customer's device
     * @return
     */
    @PostMapping("/api/user/token")
    public GeneralAnswer createPushToken(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                         @RequestBody CreatePushTokenRequest request) {
        UserAuthChecker.checkAuth(userPrincipal);
        return pushTokenService.createPushToken(request, userPrincipal.getId());
    }

    /**
     * Deleting customer's push tokens
     *
     * @param userPrincipal - authenticated customer
     * @return
     */
    @DeleteMapping("/api/user/token")
    public GeneralAnswer deleteTokens(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        UserAuthChecker.checkAuth(userPrincipal);
        return pushTokenService.deleteTokens(userPrincipal.getId());
    }
}
