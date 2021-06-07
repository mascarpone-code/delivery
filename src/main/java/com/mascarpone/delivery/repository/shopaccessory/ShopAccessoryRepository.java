package com.mascarpone.delivery.repository.shopaccessory;

import com.mascarpone.delivery.entity.shopaccessory.ShopAccessory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShopAccessoryRepository extends JpaRepository<ShopAccessory, Long> {
    Optional<ShopAccessory> findByAccessoryIdAndShopId(Long accessoryId, Long shopId);

    List<ShopAccessory> findAllByShopId(Long shopId);
}
