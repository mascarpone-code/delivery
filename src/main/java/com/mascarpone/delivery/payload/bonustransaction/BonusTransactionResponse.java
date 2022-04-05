package com.mascarpone.delivery.payload.bonustransaction;

import com.mascarpone.delivery.entity.bonustransaction.BonusTransaction;
import com.mascarpone.delivery.entity.enums.BonusTransactionType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class BonusTransactionResponse {
    private Long id;
    private UUID customerId;
    private String customerPhoneNumber;
    private String customerName;
    private Long orderId;
    private Long dateCreate;
    private BonusTransactionType type;
    private BigDecimal amount;

    public BonusTransactionResponse(BonusTransaction transaction) {
        id = transaction.getId();
        customerId = transaction.getCustomer().getUuid();
        customerPhoneNumber = transaction.getCustomer().getLogin();
        customerName = transaction.getCustomer().getName();
        orderId = transaction.getOrder().getId();
        dateCreate = transaction.getDateCreate().getTime();
        type = transaction.getType();
        amount = transaction.getBonusAmount();
    }
}
