package com.mascarpone.delivery.entity.bonustransaction;

import com.mascarpone.delivery.entity.enums.BonusTransactionType;
import lombok.Data;

import java.util.Date;

@Data
public class BonusTransactionFilter {
    private Long customerId;
    private Long shopId;
    private Date dateFrom;
    private Date dateTo;
    private BonusTransactionType type;
    private String customerName;
    private String customerPhoneNumber;
}
