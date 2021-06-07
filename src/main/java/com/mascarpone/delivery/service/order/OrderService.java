package com.mascarpone.delivery.service.order;

import com.mascarpone.delivery.entity.enums.OrderStatus;
import com.mascarpone.delivery.entity.enums.UserOrderType;
import com.mascarpone.delivery.entity.order.UserOrder;
import com.mascarpone.delivery.entity.shop.Shop;
import com.mascarpone.delivery.service.GeneralService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public interface OrderService extends GeneralService<UserOrder> {
    Page<UserOrder> findAllByShopIdOrderByDateCreateDesc(Long shopId, int page, int size);

    Optional<UserOrder> findById(Long id);

    Page<UserOrder> findAllByCreatorIdOrderByDateCreateDesc(Long userId, int page, int size);

    Optional<UserOrder> findByIdAndCreatorId(Long id, Long userId);

    Long countAllByShopId(Long shopId);

    Optional<UserOrder> findByUuid(String uuid);

    Optional<UserOrder> findByUuidIos(Long uuidIos);

    Page<UserOrder> findAllByCourierIdAndStatusOrderByDateCreateDesc(Long userId, OrderStatus orderStatus, int page, int size);

    Page<UserOrder> findAllByShopIdAndStatusOrderByDateCreateDesc(Long shopId, OrderStatus orderStatus, int page, int size);

    Optional<UserOrder> findByIdAndCourierId(Long id, Long courierId);

    Optional<UserOrder> findByIdAndShopId(Long id, Long shopId);

    List<UserOrder> findAllByIsPaidFalse();

    Optional<UserOrder> findByCreatorIdAndUserOrderType(Long creatorId, UserOrderType userOrderType);

    Optional<UserOrder> findByCreatorIdAndUserOrderTypeAndPaidIsTrue(Long creatorId, UserOrderType userOrderType);

    UserOrder findFirstByShopIdOrderByDateCreateDesc(Long shopId);

    List<UserOrder> findAllByShopIdAndOrderNumberNotNullOrderByDateCreateDesc(Long shopId);

    Page<UserOrder> findAllByShopBranchIdAndStatusAndPaidIsTrueOrderByDateCreateDesc(Long branchId, OrderStatus status, int page, int size);

    Page<UserOrder> findAllByShopIdAndStatusAndPaidIsTrueOrderByDateCreateDesc(Long shopId, OrderStatus status, int page, int size);

    Page<UserOrder> getOrdersForKitchen(Shop shop, boolean paid, OrderStatus status1, OrderStatus status2, int page, int size);

    List<UserOrder> findAllByShopId(Long shopId);

    List<UserOrder> findAllByShopIdAndDateCreateBetween(Long shopId, Date dateFrom, Date dateTo);

    Long countAllByShopIdAndDateCreateBetween(Long shopId, Date dateFrom, Date dateTo);

    void delete(UserOrder order);

    ResponseEntity<?> createOrder(UserOrder order, Long customerId) throws IOException;

    ResponseEntity<?> getOrdersByUser(Optional<Integer> page, Long customerId);
}
