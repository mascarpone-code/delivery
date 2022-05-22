package com.mascarpone.delivery.repository.productgroup;

import com.mascarpone.delivery.entity.productgroup.ProductGroup;
import com.mascarpone.delivery.entity.shop.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface ProductGroupRepository extends JpaRepository<ProductGroup, Long>, JpaSpecificationExecutor<ProductGroup> {
    Page<ProductGroup> findAllByShopId(Long shopId, Pageable pageable);

    List<ProductGroup> findAllByShopIdAndActiveTrueOrderByOrdinalNumber(Long shopId);

    boolean existsByShopIdAndOrdinalNumber(Long shopId, Integer ordinalNumber);

    Optional<ProductGroup> findByIdAndShop(Long id, Shop shop);
}
