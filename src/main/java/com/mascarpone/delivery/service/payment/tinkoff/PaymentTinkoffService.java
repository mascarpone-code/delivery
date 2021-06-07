package com.mascarpone.delivery.service.payment.tinkoff;

import com.mascarpone.delivery.entity.enums.PayTerminalType;
import com.mascarpone.delivery.entity.payment.tinkoff.PaymentTinkoff;
import com.mascarpone.delivery.service.GeneralService;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

public interface PaymentTinkoffService extends GeneralService<PaymentTinkoff> {
    Optional<PaymentTinkoff> findByOrderIdAndStatusTinkoff(String orderId, String status);

    List<PaymentTinkoff> findAllByOrderIdAndStatusTinkoffOrderByPaymentDate(String orderId, String status);

    String tinkoffNotification(String tinkoffPaymentRequest, PayTerminalType payTerminalType);
}
