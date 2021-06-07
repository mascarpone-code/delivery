package com.mascarpone.delivery.repository.payterminal;

import com.mascarpone.delivery.entity.enums.PayTerminalType;
import com.mascarpone.delivery.entity.payterminal.PayTerminal;
import com.mascarpone.delivery.entity.shop.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayTerminalRepository extends JpaRepository<PayTerminal, Long> {
    PayTerminal findByShopAndTerminalType(Shop shop, PayTerminalType terminalType);
}
