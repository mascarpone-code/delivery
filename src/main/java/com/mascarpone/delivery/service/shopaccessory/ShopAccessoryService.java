package com.mascarpone.delivery.service.shopaccessory;

import com.mascarpone.delivery.entity.shopaccessory.ShopAccessory;
import com.mascarpone.delivery.service.GeneralService;

import java.util.List;
import java.util.Optional;

public interface ShopAccessoryService extends GeneralService<ShopAccessory> {
    Optional<ShopAccessory> findByAccessoryIdAndShopId(Long accessoryId, Long shopId);

    List<ShopAccessory> findAllByShopId(Long shopId);
}
