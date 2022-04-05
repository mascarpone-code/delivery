package com.mascarpone.delivery.repository.deliveryaddress;

import com.mascarpone.delivery.entity.deliveryaddress.DeliveryAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DeliveryAddressRepository extends JpaRepository<DeliveryAddress, Long> {
    List<DeliveryAddress> findAllByUserUuid(UUID uuid);

    Optional<DeliveryAddress> findByUserUuidAndCurrentIsTrue(UUID uuid);

    Optional<DeliveryAddress> findByIdAndUserUuid(Long id, UUID uuid);
}
