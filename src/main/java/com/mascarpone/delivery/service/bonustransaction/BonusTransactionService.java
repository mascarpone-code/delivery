package com.mascarpone.delivery.service.bonustransaction;

import com.mascarpone.delivery.entity.bonustransaction.BonusTransaction;
import com.mascarpone.delivery.entity.bonustransaction.BonusTransactionFilter;
import com.mascarpone.delivery.service.GeneralService;
import org.springframework.data.domain.Page;

public interface BonusTransactionService extends GeneralService<BonusTransaction> {
    /**
     * Searching bonus transactions by filter
     *
     * @param filter - bonus transaction filter dto
     * @param page - page number
     * @param size - page size
     * @return page of bonus transactions
     */
    Page<BonusTransaction> search(BonusTransactionFilter filter, int page, int size);
}
