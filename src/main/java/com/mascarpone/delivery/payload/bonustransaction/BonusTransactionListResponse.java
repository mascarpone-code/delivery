package com.mascarpone.delivery.payload.bonustransaction;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BonusTransactionListResponse {
    private Long totalTransactionsCount;
    private Double enrollmentSum;
    private Double writeOffSum;
    private List<BonusTransactionResponse> transactions;

    public BonusTransactionListResponse(Long totalTransactionsCount,
                                        Double enrollmentSum,
                                        Double writeOffSum,
                                        List<BonusTransactionResponse> responses) {
        this.totalTransactionsCount = totalTransactionsCount;
        this.enrollmentSum = enrollmentSum;
        this.writeOffSum = writeOffSum;
        this.transactions = responses;
    }
}
