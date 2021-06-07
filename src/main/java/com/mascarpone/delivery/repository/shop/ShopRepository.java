package com.mascarpone.delivery.repository.shop;

import com.mascarpone.delivery.entity.shop.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
    Optional<Shop> findByPrefix(String prefix);
}
