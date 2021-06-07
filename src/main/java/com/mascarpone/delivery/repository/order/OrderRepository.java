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
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<UserOrder, Long> {
    Page<UserOrder> findAllByShopIdOrderByDateCreateDesc(Long shopId, Pageable pageable);

    Page<UserOrder> findAllByCreatorIdOrderByDateCreateDesc(Long userId, Pageable pageable);

    Optional<UserOrder> findByIdAndCreatorId(Long id, Long userId);

    Long countAllByShopId(Long shopId);

    Optional<UserOrder> findByUUID(String uuid);

    Optional<UserOrder> findByUUIDIos(Long uuidIos);

    Page<UserOrder> findAllByCourierIdAndStatusOrderByDateCreateDesc(Long userId, OrderStatus orderStatus, Pageable pageable);

    Page<UserOrder> findAllByShopIdAndStatusOrderByDateCreateDesc(Long shopId, OrderStatus orderStatus, Pageable pageable);

    Optional<UserOrder> findByIdAndCourierId(Long id, Long courierId);

    Optional<UserOrder> findByIdAndShopId(Long id, Long shopId);

    List<UserOrder> findAllByPaidIsFalse();

    Optional<UserOrder> findByCreatorIdAndUserOrderType(Long creatorId, UserOrderType userOrderType);

    Optional<UserOrder> findByCreatorIdAndUserOrderTypeAndPaidIsTrue(Long creatorId, UserOrderType userOrderTyped);

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

