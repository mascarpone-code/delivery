package com.mascarpone.delivery.repository.payment.tinkoff;

import com.mascarpone.delivery.entity.payment.tinkoff.PaymentTinkoff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentTinkoffRepository extends JpaRepository<PaymentTinkoff, Long> {
    Optional<PaymentTinkoff> findByOrderIdAndStatusTinkoff(String orderId, String status);

    List<PaymentTinkoff> findAllByOrderIdAndStatusTinkoffOrderByPaymentDate(String orderId, String status);
}
