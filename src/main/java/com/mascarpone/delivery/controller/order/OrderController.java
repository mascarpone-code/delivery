package com.mascarpone.delivery.controller.order;

import com.mascarpone.delivery.entity.order.UserOrder;
import com.mascarpone.delivery.security.UserPrincipal;
import com.mascarpone.delivery.service.order.OrderService;
import com.mascarpone.delivery.utils.UserAuthChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@SuppressWarnings("ALL")
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderController {
    private final OrderService orderService;

    /**
     * Customer creates order.
     *
     * @param userPrincipal - authenticated customer
     * @param order         - order entity
     * @return created order entity
     */
    @Transactional
    @PostMapping("/api/user/order")
    public ResponseEntity<?> createOrder(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                         @RequestBody UserOrder order) throws IOException {
        UserAuthChecker.checkAuth(userPrincipal);
        return orderService.createOrder(order, userPrincipal.getId());
    }

    /**
     * Customer gets a list of his orders.
     *
     * @param userPrincipal - authenticated customer
     * @param page          - page number
     * @return list of customer's orders
     */
    @GetMapping("/api/user/order")
    public ResponseEntity<?> getOrdersByUser(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                             @RequestParam("page") Optional<Integer> page) {
        UserAuthChecker.checkAuth(userPrincipal);
        return orderService.getOrdersByUser(page, userPrincipal.getId());
    }
}
