package com.mascarpone.delivery.service.deliveryaddress;

import com.mascarpone.delivery.entity.deliveryaddress.DeliveryAddress;
import com.mascarpone.delivery.service.GeneralService;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;

public interface DeliveryAddressService extends GeneralService<DeliveryAddress> {
    /**
     * Getting the user's current delivery address
     *
     * @param userUuid - user uuid
     * @return the user's current delivery address
     */
    Optional<DeliveryAddress> findByUserUuidAndCurrentIsTrue(UUID userUuid);

    /**
     * Creating and updating customer's delivery address.
     *
     * @param address    - customer's delivery address entity
     * @param customerUuid - customer uuid
     * @return customer's delivery address entity
     */
    ResponseEntity<?> addDeliveryAddress(DeliveryAddress address, UUID customerUuid);

    /**
     * Customer gets a list of his delivery addresses
     *
     * @param customerUuid - customer id
     * @return list of customer's delivery addresses
     */
    ResponseEntity<?> getDeliveryAddressesByCustomer(UUID customerUuid);

    /**
     * Customer gets his delivery address
     *
     * @param addressId  - address id
     * @param customerUuid - customer uuid
     * @return customer's deivery address entity
     */
    ResponseEntity<?> getDeliveryAddressByCustomer(Long addressId, UUID customerUuid);

    /**
     * Customer makes delivery address current.
     *
     * @param addressId  - address id
     * @param customerUuid - customer uuid
     * @return customer's deivery address entity
     */
    ResponseEntity<?> makeAddressCurrent(Long addressId, UUID customerUuid);
}
