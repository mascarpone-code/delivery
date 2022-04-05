package com.mascarpone.delivery.service.productgroup;

import com.mascarpone.delivery.entity.productgroup.ProductGroup;
import com.mascarpone.delivery.payload.GeneralAnswer;
import com.mascarpone.delivery.service.GeneralService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public interface ProductGroupService extends GeneralService<ProductGroup> {
    Page<ProductGroup> findAllByShopIdPageable(Long shopId, int page, int size);

    Page<ProductGroup> findAllByShopIdAndName(ProductGroup group, int page, int size);

    List<ProductGroup> findAllByShopIdAndActiveTrueOrderByOrdinalNumber(Long shopId);

    ResponseEntity<?> createOrUpdateProductGroup(ProductGroup productGroup, UUID shopAdminUuid);

    ResponseEntity<?> getAllProductGroupsByShopAdmin(Optional<Integer> page, Optional<String> name, UUID shopAdminUuid);

    ResponseEntity<?> getProductGroupByShopAdmin(Long id, UUID shopAdminUuid);

    GeneralAnswer deleteProductGroup(Long id, UUID shopAdminUuid);

    ResponseEntity<?> getProductGroupsByCustomer(Long shopId);
}
