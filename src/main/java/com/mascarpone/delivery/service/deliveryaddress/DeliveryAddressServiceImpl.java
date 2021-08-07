package com.mascarpone.delivery.service.deliveryaddress;

import com.mascarpone.delivery.entity.deliveryaddress.DeliveryAddress;
import com.mascarpone.delivery.entity.user.User;
import com.mascarpone.delivery.exception.BadRequestException;
import com.mascarpone.delivery.repository.deliveryaddress.DeliveryAddressRepository;
import com.mascarpone.delivery.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.mascarpone.delivery.exception.ExceptionConstants.ADDRESS_NOT_FOUND;
import static com.mascarpone.delivery.exception.ExceptionConstants.NO_ACTIVE_ADDRESS;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DeliveryAddressServiceImpl implements DeliveryAddressService {
    private final DeliveryAddressRepository deliveryAddressRepository;
    private final UserRepository userRepository;

    @Override
    public List<DeliveryAddress> getAll() {
        return deliveryAddressRepository.findAll();
    }

    @Override
    public void save(DeliveryAddress object) {
        deliveryAddressRepository.save(object);
    }

    @Override
    public List<DeliveryAddress> findAllByUserId(Long id) {
        return deliveryAddressRepository.findAllByUserId(id);
    }

    @Override
    public Optional<DeliveryAddress> findById(Long id) {
        return deliveryAddressRepository.findById(id);
    }

    @Override
    public Optional<DeliveryAddress> findByUserIdAndCurrentIsTrue(Long userId) {
        return deliveryAddressRepository.findByUserIdAndCurrentIsTrue(userId);
    }

    @Override
    public Optional<DeliveryAddress> findByIdAndUserId(Long id, Long userId) {
        return deliveryAddressRepository.findByIdAndUserId(id, userId);
    }

    /**
     * Creating and updating customer's delivery address.
     *
     * @param address    - customer's delivery address entity
     * @param customerId - customer id
     * @return customer's delivery address entity
     */
    @Override
    public ResponseEntity<?> addDeliveryAddress(DeliveryAddress address, Long customerId) {
        var currentUser = userRepository.getOne(customerId);
        var customerAddresses = deliveryAddressRepository.findAllByUser(currentUser);

        if (customerAddresses.isEmpty()) {
            address.setCurrent(true);
        }

        if (address.getCurrent() == null) {
            if (address.getId() == null) {
                address.setCurrent(false);
            } else {
                var requestedAddress = deliveryAddressRepository.getOne(address.getId());
                address.setCurrent(requestedAddress.getCurrent());
            }
        } else if (address.getCurrent()) {
            customerAddresses.forEach(customerAddress -> {
                customerAddress.setCurrent(false);
                deliveryAddressRepository.save(customerAddress);
            });
        } else {
            throw new BadRequestException(NO_ACTIVE_ADDRESS);
        }

        if (address.getId() != null) {
            var requestedAddress = deliveryAddressRepository.getOne(address.getId());
            address.setUser(requestedAddress.getUser());
        } else {
            address.setUser(currentUser);
        }

        deliveryAddressRepository.save(address);

        return ResponseEntity.ok(address);
    }

    /**
     * Customer gets a list of his delivery addresses
     *
     * @param customerId - customer id
     * @return list of customer's delivery addresses
     */
    @Override
    public ResponseEntity<?> getDeliveryAddressesByCustomer(Long customerId) {
        var deliveryAddresses = deliveryAddressRepository.findAllByUserId(customerId);

        return ResponseEntity.ok(deliveryAddresses);
    }

    /**
     * Customer gets his delivery address
     *
     * @param addressId  - address id
     * @param customerId - customer id
     * @return customer's deivery address entity
     */
    @Override
    public ResponseEntity<?> getDeliveryAddressByCustomer(Long addressId, Long customerId) {
        var address = deliveryAddressRepository.findByIdAndUserId(addressId, customerId)
                .orElseThrow(() -> new BadRequestException(ADDRESS_NOT_FOUND));

        return ResponseEntity.ok(address);
    }

    /**
     * Customer makes delivery address current.
     *
     * @param addressId  - address id
     * @param customerId - customer id
     * @return customer's deivery address entity
     */
    @Override
    public ResponseEntity<?> makeAddressCurrent(Long addressId, Long customerId) {
        var currentUser = userRepository.getOne(customerId);
        var address = deliveryAddressRepository.findByIdAndUserId(addressId, currentUser.getId())
                .orElseThrow(() -> new BadRequestException(ADDRESS_NOT_FOUND));
        var addresses = deliveryAddressRepository.findAllByUserId(currentUser.getId());

        addresses.forEach(a -> {
            a.setCurrent(false);
            deliveryAddressRepository.save(a);
        });

        address.setCurrent(true);
        deliveryAddressRepository.save(address);

        return ResponseEntity.ok(address);
    }
}
