package com.mascarpone.delivery.service.bonustransaction;

import com.mascarpone.delivery.entity.bonustransaction.BonusTransaction;
import com.mascarpone.delivery.entity.bonustransaction.BonusTransactionFilter;
import com.mascarpone.delivery.service.GeneralService;
import org.springframework.data.domain.Page;

public interface BonusTransactionService extends GeneralService<BonusTransaction> {
    Page<BonusTransaction> search(BonusTransactionFilter filter, int page, int size);
}
