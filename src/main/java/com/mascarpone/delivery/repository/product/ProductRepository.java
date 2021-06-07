package com.mascarpone.delivery.repository.product;

import com.mascarpone.delivery.entity.product.Product;
import com.mascarpone.delivery.entity.productgroup.ProductGroup;
import com.mascarpone.delivery.entity.shop.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    Page<Product> findAllByShopId(Long shopId, Pageable pageable);

    Optional<Product> findByIdAndShop(Long id, Shop shop);

    List<Product> findAllByProductGroup(ProductGroup productGroup);

    List<Product> findAllByProductGroupAndActiveIsTrue(ProductGroup productGroup);

    List<Product> findAllByShop(Shop shop);
}
