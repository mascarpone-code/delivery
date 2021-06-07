package com.mascarpone.delivery.service.productgroup;

import com.mascarpone.delivery.entity.productgroup.ProductGroup;
import com.mascarpone.delivery.payload.GeneralAnswer;
import com.mascarpone.delivery.service.GeneralService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public interface ProductGroupService extends GeneralService<ProductGroup> {
    Page<ProductGroup> findAllByShopIdPageable(Long shopId, int page, int size);

    Page<ProductGroup> findAllByShopIdAndName(ProductGroup group, int page, int size);

    Optional<ProductGroup> findById(Long id);

    List<ProductGroup> findAllByShopIdAndActiveTrueOrderByOrdinalNumber(Long shopId);

    ResponseEntity<?> createOrUpdateProductGroup(ProductGroup productGroup, Long shopAdminId);

    ResponseEntity<?> getAllProductGroupsByShopAdmin(Optional<Integer> page, Optional<String> name, Long shopAdminId);

    ResponseEntity<?> getProductGroupByShopAdmin(Long id, Long shopAdminId);

    GeneralAnswer deleteProductGroup(Long id, Long shopAdminId);

    ResponseEntity<?> getProductGroupsByCustomer(Long shopId);
}
