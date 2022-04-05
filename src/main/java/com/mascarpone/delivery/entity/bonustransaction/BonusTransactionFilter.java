package com.mascarpone.delivery.entity.bonustransaction;

import com.mascarpone.delivery.entity.enums.BonusTransactionType;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BonusTransactionFilter {
    private Long customerId;
    private Long shopId;
    private Date dateFrom;
    private Date dateTo;
    private BonusTransactionType type;
    private String customerName;
    private String customerPhoneNumber;
}
