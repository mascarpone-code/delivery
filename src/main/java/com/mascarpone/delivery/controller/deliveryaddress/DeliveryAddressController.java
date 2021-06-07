package com.mascarpone.delivery.controller.deliveryaddress;

import com.mascarpone.delivery.entity.deliveryaddress.DeliveryAddress;
import com.mascarpone.delivery.security.UserPrincipal;
import com.mascarpone.delivery.service.deliveryaddress.DeliveryAddressService;
import com.mascarpone.delivery.utils.UserAuthChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DeliveryAddressController {
    private final DeliveryAddressService deliveryAddressService;

    /**
     * Creating and updating customer's delivery address.
     *
     * @param userPrincipal - authenticated customer
     * @param address       - customer's delivery address entity
     * @return customer's delivery address entity
     */
    @Transactional
    @PostMapping("/api/user/address")
    public ResponseEntity<?> addDeliveryAddress(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                @RequestBody DeliveryAddress address) {
        UserAuthChecker.checkAuth(userPrincipal);
        return deliveryAddressService.addDeliveryAddress(address, userPrincipal.getId());
    }

    /**
     * Customer gets a list of his delivery addresses
     *
     * @param userPrincipal - authenticated customer
     * @return list of customer's delivery addresses
     */
    @GetMapping("/api/user/address")
    public ResponseEntity<?> getDeliveryAddresses(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        UserAuthChecker.checkAuth(userPrincipal);
        return deliveryAddressService.getDeliveryAddressesByCustomer(userPrincipal.getId());
    }

    /**
     * Customer gets his delivery address
     *
     * @param userPrincipal - authenticated customer
     * @param id            - address id
     * @return customer's deivery address entity
     */
    @GetMapping("/api/user/address/{id}")
    public ResponseEntity<?> getDeliveryAddress(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                @PathVariable("id") Long id) {
        UserAuthChecker.checkAuth(userPrincipal);
        return deliveryAddressService.getDeliveryAddressByCustomer(id, userPrincipal.getId());
    }

    /**
     * Customer makes delivery address current.
     *
     * @param userPrincipal - authenticated customer
     * @param id            - address id
     * @return customer's deivery address entity
     */
    @PutMapping("/api/user/address/current/{id}")
    public ResponseEntity<?> makeAddressCurrent(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                @PathVariable("id") Long id) {
        UserAuthChecker.checkAuth(userPrincipal);
        return deliveryAddressService.makeAddressCurrent(id, userPrincipal.getId());
    }
}
