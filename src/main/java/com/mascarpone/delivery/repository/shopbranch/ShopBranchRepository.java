package com.mascarpone.delivery.repository.shopbranch;

import com.mascarpone.delivery.entity.shop.Shop;
import com.mascarpone.delivery.entity.shopbranch.ShopBranch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShopBranchRepository extends JpaRepository<ShopBranch, Long>, JpaSpecificationExecutor<ShopBranch> {
    List<ShopBranch> findAllByShopIdAndActiveTrueOrderByAddressAsc(Long shopId);

    Optional<ShopBranch> findByIdAndShop(Long id, Shop shop);

    Optional<ShopBranch> findByIdAndShopId(Long id, Long shopId);
}
