package com.mascarpone.delivery.repository.bonustransaction;

import com.mascarpone.delivery.entity.bonustransaction.BonusTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BonusTransactionRepository extends JpaRepository<BonusTransaction, Long>, JpaSpecificationExecutor<BonusTransaction> {
}
