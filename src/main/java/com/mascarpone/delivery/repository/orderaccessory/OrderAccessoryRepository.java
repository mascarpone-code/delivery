package com.mascarpone.delivery.repository.orderaccessory;

import com.mascarpone.delivery.entity.orderaccessory.OrderAccessory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderAccessoryRepository extends JpaRepository<OrderAccessory, Long> {
}
