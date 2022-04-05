package com.mascarpone.delivery.service.pushtoken;

import com.mascarpone.delivery.entity.pushtoken.PushToken;
import com.mascarpone.delivery.payload.GeneralAnswer;
import com.mascarpone.delivery.payload.pushtoken.CreatePushTokenRequest;
import com.mascarpone.delivery.service.GeneralService;

import java.util.List;
import java.util.UUID;

public interface PushTokenService extends GeneralService<PushToken> {
    boolean existsByUserUuidAndToken(UUID userUuid, String token);

    long countAllByUserUuid(UUID userUuid);

    List<PushToken> findAllByUserUuidOrderByIdAsc(UUID userUuid);

    List<PushToken> findAllByUserUuid(UUID userUuid);

    GeneralAnswer createPushToken(CreatePushTokenRequest request, UUID customerUuid);

    GeneralAnswer deleteTokens(UUID customerUuid);
}
