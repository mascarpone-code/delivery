package com.mascarpone.delivery.repository.order;

import com.mascarpone.delivery.entity.enums.OrderStatus;
import com.mascarpone.delivery.entity.enums.UserOrderType;
import com.mascarpone.delivery.entity.order.UserOrder;
import com.mascarpone.delivery.entity.shop.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<UserOrder, Long> {
    Page<UserOrder> findAllByShopIdOrderByDateCreateDesc(Long shopId, Pageable pageable);

    Page<UserOrder> findAllByCreatorUuidOrderByDateCreateDesc(UUID userUuid, Pageable pageable);

    Optional<UserOrder> findByIdAndCreatorUuid(Long id, UUID userUuid);

    Long countAllByShopId(Long shopId);

    Optional<UserOrder> findByUUID(String uuid);

    Optional<UserOrder> findByUUIDIos(Long uuidIos);

    Page<UserOrder> findAllByCourierUuidAndStatusOrderByDateCreateDesc(UUID userUuid, OrderStatus orderStatus, Pageable pageable);

    Page<UserOrder> findAllByShopIdAndStatusOrderByDateCreateDesc(Long shopId, OrderStatus orderStatus, Pageable pageable);

    Optional<UserOrder> findByIdAndCourierUuid(Long id, UUID courierUuid);

    Optional<UserOrder> findByIdAndShopId(Long id, Long shopId);

    List<UserOrder> findAllByPaidIsFalse();

    Optional<UserOrder> findByCreatorUuidAndUserOrderType(UUID creatorUuid, UserOrderType userOrderType);

    Optional<UserOrder> findByCreatorUuidAndUserOrderTypeAndPaidIsTrue(UUID creatorUuid, UserOrderType userOrderTyped);

    UserOrder findFirstByShopIdOrderByDateCreateDesc(Long shopId);

    UserOrder findFirstByShopOrderByDateCreateDesc(Shop shop);

    List<UserOrder> findAllByShopIdAndOrderNumberNotNullOrderByDateCreateDesc(Long shopId);

    Page<UserOrder> findAllByShopBranchIdAndStatusAndPaidIsTrueOrderByDateCreateDesc(Long branchId, OrderStatus status, Pageable pageable);

    Page<UserOrder> findAllByShopIdAndStatusAndPaidIsTrueOrderByDateCreateDesc(Long shopId, OrderStatus status, Pageable pageable);

    @Query("select o from UserOrder o where o.shop = :shop and o.paid = :paid and o.status = :status1 or o.status = :status2")
    Page<UserOrder> getOrdersForKitchen(@Param("shop") Shop shop,
                                        @Param("paid") boolean paid,
                                        @Param("status1") OrderStatus status1,
                                        @Param("status2") OrderStatus status2,
                                        Pageable pageable);

    List<UserOrder> findAllByShopId(Long shopId);

    List<UserOrder> findAllByShopIdAndDateCreateBetween(Long shopId, Date dateFrom, Date dateTo);

    Long countAllByShopIdAndDateCreateBetween(Long shopId, Date dateFrom, Date dateTo);
}

