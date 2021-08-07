package com.mascarpone.delivery.job;

import com.mascarpone.delivery.service.nomenclature.NomenclatureService;
import com.mascarpone.delivery.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.mascarpone.delivery.utils.Constants.MILLIS_IN_12_HOURS;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderScheduler {
    private final OrderService orderService;
    private final NomenclatureService nomenclatureService;


    @Scheduled(cron = "0 0 5 * * ?", zone = "Europe/Moscow")
    public void checkOrders() {
        var dateNow = new Date().getTime();
        var notPaidOrders = orderService.findAllByIsPaidFalse();

        for (var order : notPaidOrders) {
            if (dateNow - order.getDateCreate().getTime() > MILLIS_IN_12_HOURS) {
                orderService.delete(order);
            }
        }
    }

    @Scheduled(cron = "0 0 1 * * ?", zone = "Europe/Moscow")
    public void getAndSendNomenclatureOrders() {
        nomenclatureService.getAndSendNomenclatureForOrder();
    }
}
