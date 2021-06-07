package com.mascarpone.delivery.service.modifier;

import com.mascarpone.delivery.entity.modifier.Modifier;
import com.mascarpone.delivery.entity.product.Product;
import com.mascarpone.delivery.payload.GeneralAnswer;
import com.mascarpone.delivery.service.GeneralService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public interface ModifierService extends GeneralService<Modifier> {
    Page<Modifier> findAllByShopId(Long shopId, int page, int size);

    Page<Modifier> findAllByShopIdAndName(Modifier modifier, int page, int size);

    Optional<Modifier> findByIdAndProduct(Long id, Product product);

    ResponseEntity<?> createOrUpdateModifier(Modifier modifier, Long shopAdminId);

    ResponseEntity<?> getAllModifiersShop(Optional<Integer> page, Optional<String> name, Long shopAdminId);

    ResponseEntity<?> getModifierShop(Long id, Long shopAdminId);

    GeneralAnswer deleteModifier(Long id, Long shopAdminId);

    ResponseEntity<?> getProductModifiersByCustomer(Long productId);

    ResponseEntity<?> getModifierByCustomer(Long id);
}
