package com.mascarpone.delivery.repository.pushtoken;

import com.mascarpone.delivery.entity.pushtoken.PushToken;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PushTokenRepository extends JpaRepository<PushToken, Long> {
    boolean existsByUserUuidAndToken(UUID userUuid, String token);

    long countAllByUserUuid(UUID userUuid);

    List<PushToken> findAllByUserUuidOrderByIdAsc(UUID userUuid, Pageable pageable);

    List<PushToken> findAllByUserUuid(UUID userUuid);
}
