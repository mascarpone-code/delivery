package com.mascarpone.delivery.repository.deliveryarea;

import com.mascarpone.delivery.entity.deliveryarea.DeliveryArea;
import com.mascarpone.delivery.entity.shop.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeliveryAreaRepository extends JpaRepository<DeliveryArea, Long> {
    List<DeliveryArea> findAllByShopId(Long shopId);

    List<DeliveryArea> findAllByShop(Shop shop);
}
