package com.mascarpone.delivery.service.payterminal;

import com.mascarpone.delivery.entity.enums.PayTerminalType;
import com.mascarpone.delivery.entity.payterminal.PayTerminal;
import com.mascarpone.delivery.entity.shop.Shop;
import com.mascarpone.delivery.service.GeneralService;

public interface PayTerminalService extends GeneralService<PayTerminal> {
    PayTerminal findByShopAndTerminalType(Shop shop, PayTerminalType terminalType);
}
