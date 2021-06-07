package com.mascarpone.delivery.service.deliveryaddress;

import com.mascarpone.delivery.entity.deliveryaddress.DeliveryAddress;
import com.mascarpone.delivery.service.GeneralService;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface DeliveryAddressService extends GeneralService<DeliveryAddress> {
    List<DeliveryAddress> findAllByUserId(Long id);

    Optional<DeliveryAddress> findByUserIdAndCurrentIsTrue(Long userId);

    Optional<DeliveryAddress> findByIdAndUserId(Long id, Long userId);

    ResponseEntity<?> addDeliveryAddress(DeliveryAddress address, Long customerId);

    ResponseEntity<?> getDeliveryAddressesByCustomer(Long customerId);

    ResponseEntity<?> getDeliveryAddressByCustomer(Long addressId, Long customerId);

    ResponseEntity<?> makeAddressCurrent(Long addressId, Long customerId);
}
