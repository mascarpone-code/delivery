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
import java.util.UUID;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public interface OrderService extends GeneralService<UserOrder> {
    Page<UserOrder> findAllByShopIdOrderByDateCreateDesc(Long shopId, int page, int size);

    Page<UserOrder> findAllByCreatorUuidOrderByDateCreateDesc(UUID userUuid, int page, int size);

    Optional<UserOrder> findByIdAndCreatorUuid(Long id, UUID userUuid);

    Long countAllByShopId(Long shopId);

    Optional<UserOrder> findByUuid(String uuid);

    Optional<UserOrder> findByUuidIos(Long uuidIos);

    Page<UserOrder> findAllByCourierUuidAndStatusOrderByDateCreateDesc(UUID userUuid, OrderStatus orderStatus, int page, int size);

    Page<UserOrder> findAllByShopIdAndStatusOrderByDateCreateDesc(Long shopId, OrderStatus orderStatus, int page, int size);

    Optional<UserOrder> findByIdAndCourierId(Long id, UUID courierUuid);

    Optional<UserOrder> findByIdAndShopId(Long id, Long shopId);

    /**
     * Getting all unpaid orders
     *
     * @return list of unpaid orders
     */
    List<UserOrder> findAllByIsPaidFalse();

    Optional<UserOrder> findByCreatorUuidAndUserOrderType(UUID creatorUuid, UserOrderType userOrderType);

    Optional<UserOrder> findByCreatorUuidAndUserOrderTypeAndPaidIsTrue(UUID creatorUuid, UserOrderType userOrderType);

    UserOrder findFirstByShopIdOrderByDateCreateDesc(Long shopId);

    List<UserOrder> findAllByShopIdAndOrderNumberNotNullOrderByDateCreateDesc(Long shopId);

    Page<UserOrder> findAllByShopBranchIdAndStatusAndPaidIsTrueOrderByDateCreateDesc(Long branchId, OrderStatus status, int page, int size);

    Page<UserOrder> findAllByShopIdAndStatusAndPaidIsTrueOrderByDateCreateDesc(Long shopId, OrderStatus status, int page, int size);

    Page<UserOrder> getOrdersForKitchen(Shop shop, boolean paid, OrderStatus status1, OrderStatus status2, int page, int size);

    List<UserOrder> findAllByShopId(Long shopId);

    List<UserOrder> findAllByShopIdAndDateCreateBetween(Long shopId, Date dateFrom, Date dateTo);

    Long countAllByShopIdAndDateCreateBetween(Long shopId, Date dateFrom, Date dateTo);

    /**
     * Deleting the order
     *
     * @param order - order entity
     */
    void delete(UserOrder order);

    /**
     * Customer creates order.
     *
     * @param order      - order entity
     * @param customerUuid - customer's id
     * @return created order entity
     * @throws IOException
     */
    ResponseEntity<?> createOrder(UserOrder order, UUID customerUuid) throws IOException;

    /**
     * Customer gets a list of his orders.
     *
     * @param page       - page number
     * @param customerUuid - customer's id
     * @return list of customer's orders
     */
    ResponseEntity<?> getOrdersByUser(Optional<Integer> page, UUID customerUuid);
}
