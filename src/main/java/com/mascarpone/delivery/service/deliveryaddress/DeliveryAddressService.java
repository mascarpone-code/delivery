package com.mascarpone.delivery.service.deliveryaddress;

import com.mascarpone.delivery.entity.deliveryaddress.DeliveryAddress;
import com.mascarpone.delivery.service.GeneralService;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface DeliveryAddressService extends GeneralService<DeliveryAddress> {
    /**
     * Getting the user's current delivery address
     *
     * @param userId - user id
     * @return the user's current delivery address
     */
    Optional<DeliveryAddress> findByUserIdAndCurrentIsTrue(Long userId);

    /**
     * Creating and updating customer's delivery address.
     *
     * @param address    - customer's delivery address entity
     * @param customerId - customer id
     * @return customer's delivery address entity
     */
    ResponseEntity<?> addDeliveryAddress(DeliveryAddress address, Long customerId);

    /**
     * Customer gets a list of his delivery addresses
     *
     * @param customerId - customer id
     * @return list of customer's delivery addresses
     */
    ResponseEntity<?> getDeliveryAddressesByCustomer(Long customerId);

    /**
     * Customer gets his delivery address
     *
     * @param addressId  - address id
     * @param customerId - customer id
     * @return customer's deivery address entity
     */
    ResponseEntity<?> getDeliveryAddressByCustomer(Long addressId, Long customerId);

    /**
     * Customer makes delivery address current.
     *
     * @param addressId  - address id
     * @param customerId - customer id
     * @return customer's deivery address entity
     */
    ResponseEntity<?> makeAddressCurrent(Long addressId, Long customerId);
}
