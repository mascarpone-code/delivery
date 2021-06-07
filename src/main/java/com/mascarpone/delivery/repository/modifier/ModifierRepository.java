package com.mascarpone.delivery.repository.modifier;

import com.mascarpone.delivery.entity.modifier.Modifier;
import com.mascarpone.delivery.entity.product.Product;
import com.mascarpone.delivery.entity.shop.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ModifierRepository extends JpaRepository<Modifier, Long>, JpaSpecificationExecutor<Modifier> {
    Page<Modifier> findAllByShopIdOrderByDateCreateDesc(Long shopId, Pageable pageable);

    List<Modifier> findAllByProducts(Product product);

    Optional<Modifier> findByIdAndProducts(Long id, Product product);

    Optional<Modifier> findByIdAndShop(Long id, Shop shop);
}
