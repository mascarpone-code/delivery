package com.mascarpone.delivery.service.shop;

import com.mascarpone.delivery.entity.shop.Shop;
import com.mascarpone.delivery.payload.shop.UpdateShopInfoRequest;
import com.mascarpone.delivery.service.GeneralService;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface ShopService extends GeneralService<Shop> {
    Optional<Shop> findByPrefix(String prefix);

    ResponseEntity<?> updateShopInfo(UpdateShopInfoRequest request, Long shopAdminId);
}
