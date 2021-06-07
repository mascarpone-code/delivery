package com.mascarpone.delivery.controller.payments;

import com.mascarpone.delivery.entity.enums.PayTerminalType;
import com.mascarpone.delivery.service.payment.tinkoff.PaymentTinkoffService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TinkoffNotificationsController {
    private final PaymentTinkoffService paymentTinkoffService;

    @PostMapping("/api/tinkoff/notification/mobile")
    public String tinkoffNotification(@RequestBody String tinkoffPaymentRequest) {
        return paymentTinkoffService.tinkoffNotification(tinkoffPaymentRequest, PayTerminalType.TINKOFF_MOBILE);
    }

    @PostMapping("/api/tinkoff/notification/web")
    public String tinkoffWebNotification(@RequestBody String tinkoffPaymentRequest) {
        return paymentTinkoffService.tinkoffNotification(tinkoffPaymentRequest, PayTerminalType.TINKOFF_WEB);
    }
}
