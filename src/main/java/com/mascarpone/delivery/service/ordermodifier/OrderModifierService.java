package com.mascarpone.delivery.service.ordermodifier;

import com.mascarpone.delivery.entity.ordermodifier.OrderModifier;
import com.mascarpone.delivery.service.GeneralService;

import java.util.List;

public interface OrderModifierService extends GeneralService<OrderModifier> {
    List<OrderModifier> findAllByModifierId(Long id);
}
