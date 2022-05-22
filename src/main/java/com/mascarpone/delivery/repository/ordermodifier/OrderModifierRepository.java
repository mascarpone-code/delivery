package com.mascarpone.delivery.repository.ordermodifier;

import com.mascarpone.delivery.entity.ordermodifier.OrderModifier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderModifierRepository extends JpaRepository<OrderModifier, Long> {
    List<OrderModifier> findAllByModifierId(Long id);
}
