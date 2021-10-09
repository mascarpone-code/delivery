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
    public Optional<DeliveryAddress> findById(Long id) {
        return deliveryAddressRepository.findById(id);
    }

    @Override
    public Optional<DeliveryAddress> findByUserIdAndCurrentIsTrue(Long userId) {
        return deliveryAddressRepository.findByUserIdAndCurrentIsTrue(userId);
    }

    @Override
    public ResponseEntity<?> addDeliveryAddress(DeliveryAddress deliveryAddress, Long customerId) {
        var currentUser = userRepository.getOne(customerId);
        var deliveryAddressList = deliveryAddressRepository.findAllByUser(currentUser);

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
    public ResponseEntity<?> getDeliveryAddressesByCustomer(Long customerId) {
        var deliveryAddresses = deliveryAddressRepository.findAllByUserId(customerId);

        return ResponseEntity.ok(deliveryAddresses);
    }

    @Override
    public ResponseEntity<?> getDeliveryAddressByCustomer(Long addressId, Long customerId) {
        var deliveryAddress = deliveryAddressRepository.findByIdAndUserId(addressId, customerId)
                .orElseThrow(() -> new BadRequestException(ADDRESS_NOT_FOUND));

        return ResponseEntity.ok(deliveryAddress);
    }

    @Override
    public ResponseEntity<?> makeAddressCurrent(Long addressId, Long customerId) {
        var currentUser = userRepository.getOne(customerId);
        var deliveryAddress = deliveryAddressRepository.findByIdAndUserId(addressId, currentUser.getId())
                .orElseThrow(() -> new BadRequestException(ADDRESS_NOT_FOUND));
        var deliveryAddressList = deliveryAddressRepository.findAllByUserId(currentUser.getId());

        deliveryAddressList.forEach(address -> {
            address.setCurrent(false);
            deliveryAddressRepository.save(address);
        });

        deliveryAddress.setCurrent(true);
        deliveryAddressRepository.save(deliveryAddress);

        return ResponseEntity.ok(deliveryAddress);
    }
}
