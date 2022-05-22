package com.mascarpone.delivery.repository.orderproduct;

import com.mascarpone.delivery.entity.orderproduct.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
}
