package com.mascarpone.delivery.repository.deliveryaddress;

import com.mascarpone.delivery.entity.deliveryaddress.DeliveryAddress;
import com.mascarpone.delivery.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryAddressRepository extends JpaRepository<DeliveryAddress, Long> {
    List<DeliveryAddress> findAllByUserId(Long id);

    Optional<DeliveryAddress> findByUserIdAndCurrentIsTrue(Long userId);

    Optional<DeliveryAddress> findByIdAndUserId(Long id, Long userId);

    List<DeliveryAddress> findAllByUser(User user);
}
