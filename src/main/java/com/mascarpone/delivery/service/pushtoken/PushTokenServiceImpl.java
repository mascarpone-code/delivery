package com.mascarpone.delivery.service.pushtoken;

import com.mascarpone.delivery.entity.pushtoken.PushToken;
import com.mascarpone.delivery.payload.GeneralAnswer;
import com.mascarpone.delivery.payload.pushtoken.CreatePushTokenRequest;
import com.mascarpone.delivery.pushnotification.PushType;
import com.mascarpone.delivery.repository.pushtoken.PushTokenRepository;
import com.mascarpone.delivery.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.mascarpone.delivery.utils.Constants.CUSTOMER_PUSH_TOKEN_MAX_COUNT;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PushTokenServiceImpl implements PushTokenService {
    private final PushTokenRepository pushTokenRepository;
    private final UserRepository userRepository;

    @Override
    public List<PushToken> getAll() {
        return pushTokenRepository.findAll();
    }

    @Override
    public void save(PushToken object) {
        pushTokenRepository.save(object);
    }

    @Override
    public boolean existsByUserUuidAndToken(UUID userUuid, String token) {
        return pushTokenRepository.existsByUserUuidAndToken(userUuid, token);
    }

    @Override
    public long countAllByUserUuid(UUID userUuid) {
        return pushTokenRepository.countAllByUserUuid(userUuid);
    }

    @Override
    public List<PushToken> findAllByUserUuidOrderByIdAsc(UUID userUuid) {
        return pushTokenRepository.findAllByUserUuidOrderByIdAsc(userUuid, PageRequest.of(0, 1));
    }

    @Override
    public List<PushToken> findAllByUserUuid(UUID userUuid) {
        return pushTokenRepository.findAllByUserUuid(userUuid);
    }

    /**
     * Creating a customer's push token
     *
     * @param request    - push token and type of customer's device
     * @param customerUuid - customer's id
     * @return
     */
    @Override
    public GeneralAnswer<String> createPushToken(CreatePushTokenRequest request, UUID customerUuid) {
        var token = request.getToken();
        var device = request.getDevice();

        if (!pushTokenRepository.existsByUserUuidAndToken(customerUuid, token)) {
            var pushToken = new PushToken();
            pushToken.setDevice(PushType.fromValue(device));
            pushToken.setToken(token);
            var user = userRepository.getOne(customerUuid);
            pushToken.setUser(user);
            pushTokenRepository.save(pushToken);
        }

        var pushTokenList = pushTokenRepository.findAllByUserUuid(customerUuid);

        if (pushTokenList.size() > CUSTOMER_PUSH_TOKEN_MAX_COUNT) {
            pushTokenRepository.deleteAll(pushTokenList);
        }

        return new GeneralAnswer<>("OK", null, null);
    }

    /**
     * Deleting customer's push tokens
     *
     * @param customerUuid - customer's id
     * @return
     */
    @Override
    public GeneralAnswer<String> deleteTokens(UUID customerUuid) {
        var pushTokens = pushTokenRepository.findAllByUserUuid(customerUuid);

        if (!pushTokens.isEmpty()) {
            pushTokenRepository.deleteAll(pushTokens);
        }

        return new GeneralAnswer<>("OK", null, null);
    }
}
