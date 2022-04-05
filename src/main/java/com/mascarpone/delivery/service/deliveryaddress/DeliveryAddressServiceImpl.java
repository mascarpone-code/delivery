package com.mascarpone.delivery.service.deliveryaddress;

import com.mascarpone.delivery.entity.deliveryaddress.DeliveryAddress;
import com.mascarpone.delivery.exception.BadRequestException;
import com.mascarpone.delivery.repository.deliveryaddress.DeliveryAddressRepository;
import com.mascarpone.delivery.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    public Optional<DeliveryAddress> findByUserUuidAndCurrentIsTrue(UUID userUuid) {
        return deliveryAddressRepository.findByUserUuidAndCurrentIsTrue(userUuid);
    }

    @Override
    public ResponseEntity<?> addDeliveryAddress(DeliveryAddress deliveryAddress, UUID customerUuid) {
        var currentUser = userRepository.getOne(customerUuid);
        var deliveryAddressList = deliveryAddressRepository.findAllByUserUuid(currentUser.getUuid());

        if (deliveryAddressList.isEmpty()) {
            deliveryAddress.setCurrent(true);
        }

        if (deliveryAddress.getCurrent() == null) {
            if (deliveryAddress.getId() == null) {
                deliveryAddress.setCurrent(false);
            } else {
                var requestedAddress = deliveryAddressRepository.getOne(deliveryAddress.getId());
                deliveryAddress.setCurrent(requestedAddress.getCurrent());
            }
        } else if (deliveryAddress.getCurrent()) {
            deliveryAddressList.forEach(customerAddress -> {
                customerAddress.setCurrent(false);
                deliveryAddressRepository.save(customerAddress);
            });
        } else {
            throw new BadRequestException(NO_ACTIVE_ADDRESS);
        }

        if (deliveryAddress.getId() != null) {
            var requestedAddress = deliveryAddressRepository.getOne(deliveryAddress.getId());
            deliveryAddress.setUser(requestedAddress.getUser());
        } else {
            deliveryAddress.setUser(currentUser);
        }

        deliveryAddressRepository.save(deliveryAddress);

        return ResponseEntity.ok(deliveryAddress);
    }

    @Override
    public ResponseEntity<?> getDeliveryAddressesByCustomer(UUID customerUuid) {
        var deliveryAddresses = deliveryAddressRepository.findAllByUserUuid(customerUuid);

        return ResponseEntity.ok(deliveryAddresses);
    }

    @Override
    public ResponseEntity<?> getDeliveryAddressByCustomer(Long addressId, UUID customerUuid) {
        var deliveryAddress = deliveryAddressRepository.findByIdAndUserUuid(addressId, customerUuid)
                .orElseThrow(() -> new BadRequestException(ADDRESS_NOT_FOUND));

        return ResponseEntity.ok(deliveryAddress);
    }

    @Override
    public ResponseEntity<?> makeAddressCurrent(Long addressId, UUID customerUuid) {
        var currentUser = userRepository.getOne(customerUuid);
        var deliveryAddress = deliveryAddressRepository.findByIdAndUserUuid(addressId, currentUser.getUuid())
                .orElseThrow(() -> new BadRequestException(ADDRESS_NOT_FOUND));
        var deliveryAddressList = deliveryAddressRepository.findAllByUserUuid(currentUser.getUuid());

        deliveryAddressList.forEach(address -> {
            address.setCurrent(false);
            deliveryAddressRepository.save(address);
        });

        deliveryAddress.setCurrent(true);
        deliveryAddressRepository.save(deliveryAddress);

        return ResponseEntity.ok(deliveryAddress);
    }
}
