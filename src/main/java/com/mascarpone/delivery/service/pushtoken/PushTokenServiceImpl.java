package com.mascarpone.delivery.service.pushtoken;

import com.mascarpone.delivery.entity.pushtoken.PushToken;
import com.mascarpone.delivery.entity.user.User;
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
import java.util.Optional;

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
    public Optional<PushToken> findById(Long id) {
        return pushTokenRepository.findById(id);
    }

    @Override
    public void save(PushToken object) {
        pushTokenRepository.save(object);
    }

    @Override
    public boolean existsByUserIdAndToken(Long userId, String token) {
        return pushTokenRepository.existsByUserIdAndToken(userId, token);
    }

    @Override
    public long countAllByUserId(Long userId) {
        return pushTokenRepository.countAllByUserId(userId);
    }

    @Override
    public List<PushToken> findAllByUserIdOrderByIdAsc(Long userId) {
        return pushTokenRepository.findAllByUserIdOrderByIdAsc(userId, PageRequest.of(0, 1));
    }

    @Override
    public List<PushToken> findAllByUserId(Long userId) {
        return pushTokenRepository.findAllByUserId(userId);
    }

    /**
     * Creating a customer's push token
     *
     * @param request    - push token and type of customer's device
     * @param customerId - customer's id
     * @return
     */
    @Override
    public GeneralAnswer createPushToken(CreatePushTokenRequest request, Long customerId) {
        String token = request.getToken();
        String device = request.getDevice();

        if (!pushTokenRepository.existsByUserIdAndToken(customerId, token)) {
            PushToken pushToken = new PushToken();
            pushToken.setDevice(PushType.fromValue(device));
            pushToken.setToken(token);
            User user = userRepository.getOne(customerId);
            pushToken.setUser(user);
            pushTokenRepository.save(pushToken);
        }

        List<PushToken> pushTokenList = pushTokenRepository.findAllByUserId(customerId);

        if (pushTokenList.size() > 3) {
            pushTokenRepository.deleteAll(pushTokenList);
        }

        return new GeneralAnswer<>("OK", null, null);
    }

    /**
     * Deleting customer's push tokens
     *
     * @param customerId - customer's id
     * @return
     */
    @Override
    public GeneralAnswer deleteTokens(Long customerId) {
        List<PushToken> pushTokens = pushTokenRepository.findAllByUserId(customerId);

        if (!pushTokens.isEmpty()) {
            pushTokenRepository.deleteAll(pushTokens);
        }

        return new GeneralAnswer<>("OK", null, null);
    }
}
