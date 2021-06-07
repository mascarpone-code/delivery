package com.mascarpone.delivery.repository.ordermodifier;

import com.mascarpone.delivery.entity.ordermodifier.OrderModifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderModifierRepository extends JpaRepository<OrderModifier, Long> {
    List<OrderModifier> findAllByModifierId(Long id);
}
