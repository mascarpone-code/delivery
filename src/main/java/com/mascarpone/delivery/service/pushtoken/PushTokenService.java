package com.mascarpone.delivery.service.pushtoken;

import com.mascarpone.delivery.entity.pushtoken.PushToken;
import com.mascarpone.delivery.payload.GeneralAnswer;
import com.mascarpone.delivery.payload.pushtoken.CreatePushTokenRequest;
import com.mascarpone.delivery.service.GeneralService;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PushTokenService extends GeneralService<PushToken> {
    boolean existsByUserIdAndToken(Long userId, String token);

    long countAllByUserId(Long userId);

    List<PushToken> findAllByUserIdOrderByIdAsc(Long userId);

    List<PushToken> findAllByUserId(Long userId);

    GeneralAnswer createPushToken(CreatePushTokenRequest request, Long customerId);

    GeneralAnswer deleteTokens(Long customerId);
}
