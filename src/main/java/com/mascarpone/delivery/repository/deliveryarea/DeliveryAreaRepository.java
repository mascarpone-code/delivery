package com.mascarpone.delivery.repository.deliveryarea;

import com.mascarpone.delivery.entity.deliveryarea.DeliveryArea;
import com.mascarpone.delivery.entity.shop.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryAreaRepository extends JpaRepository<DeliveryArea, Long> {
    List<DeliveryArea> findAllByShopId(Long shopId);

    List<DeliveryArea> findAllByShop(Shop shop);
}
