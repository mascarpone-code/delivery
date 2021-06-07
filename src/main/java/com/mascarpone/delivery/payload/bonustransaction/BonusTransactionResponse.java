package com.mascarpone.delivery.payload.bonustransaction;

import com.mascarpone.delivery.entity.bonustransaction.BonusTransaction;
import com.mascarpone.delivery.entity.enums.BonusTransactionType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BonusTransactionResponse {
    private Long id;
    private Long customerId;
    private String customerPhoneNumber;
    private String customerName;
    private Long orderId;
    private Long dateCreate;
    private BonusTransactionType type;
    private BigDecimal amount;

    public BonusTransactionResponse(BonusTransaction transaction) {
        id = transaction.getId();
        customerId = transaction.getCustomer().getId();
        customerPhoneNumber = transaction.getCustomer().getLogin();
        customerName = transaction.getCustomer().getName();
        orderId = transaction.getOrder().getId();
        dateCreate = transaction.getDateCreate().getTime();
        type = transaction.getType();
        amount = transaction.getBonusAmount();
    }
}
