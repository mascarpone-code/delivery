package com.mascarpone.delivery.service.shop;

import com.mascarpone.delivery.entity.shop.Shop;
import com.mascarpone.delivery.payload.shop.UpdateShopInfoRequest;
import com.mascarpone.delivery.service.GeneralService;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;

public interface ShopService extends GeneralService<Shop> {
    Optional<Shop> findByPrefix(String prefix);

    ResponseEntity<?> updateShopInfo(UpdateShopInfoRequest request, UUID shopAdminUuid);

    Optional<Shop> findById(long shopId);
}
