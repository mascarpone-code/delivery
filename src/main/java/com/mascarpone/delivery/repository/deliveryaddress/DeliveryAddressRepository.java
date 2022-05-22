package com.mascarpone.delivery.repository.deliveryaddress;

import com.mascarpone.delivery.entity.deliveryaddress.DeliveryAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DeliveryAddressRepository extends JpaRepository<DeliveryAddress, Long> {
    List<DeliveryAddress> findAllByUserUuid(UUID uuid);

    Optional<DeliveryAddress> findByUserUuidAndCurrentIsTrue(UUID uuid);

    Optional<DeliveryAddress> findByIdAndUserUuid(Long id, UUID uuid);
}
