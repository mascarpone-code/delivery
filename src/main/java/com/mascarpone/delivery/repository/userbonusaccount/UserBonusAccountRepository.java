package com.mascarpone.delivery.repository.userbonusaccount;

import com.mascarpone.delivery.entity.userbonusaccount.UserBonusAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBonusAccountRepository extends JpaRepository<UserBonusAccount, Long> {
}
