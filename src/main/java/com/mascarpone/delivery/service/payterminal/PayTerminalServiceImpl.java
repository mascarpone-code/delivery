package com.mascarpone.delivery.service.payterminal;

import com.mascarpone.delivery.entity.enums.PayTerminalType;
import com.mascarpone.delivery.entity.payterminal.PayTerminal;
import com.mascarpone.delivery.entity.shop.Shop;
import com.mascarpone.delivery.repository.payterminal.PayTerminalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PayTerminalServiceImpl implements PayTerminalService {
    private final PayTerminalRepository repository;

    @Override
    public List<PayTerminal> getAll() {
        return repository.findAll();
    }

    @Override
    public void save(PayTerminal object) {
        repository.save(object);
    }

    @Override
    public PayTerminal findByShopAndTerminalType(Shop shop, PayTerminalType terminalType) {
        return repository.findByShopAndTerminalType(shop, terminalType);
    }
}
