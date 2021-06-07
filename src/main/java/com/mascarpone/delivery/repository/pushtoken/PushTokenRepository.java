package com.mascarpone.delivery.repository.pushtoken;

import com.mascarpone.delivery.entity.pushtoken.PushToken;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PushTokenRepository extends JpaRepository<PushToken, Long> {
    boolean existsByUserIdAndToken(Long userId, String token);

    long countAllByUserId(Long userId);

    List<PushToken> findAllByUserIdOrderByIdAsc(Long userId, Pageable pageable);

    List<PushToken> findAllByUserId(Long userId);
}
