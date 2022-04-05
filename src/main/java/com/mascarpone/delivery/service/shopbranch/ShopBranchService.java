package com.mascarpone.delivery.service.shopbranch;

import com.mascarpone.delivery.entity.shopbranch.ShopBranch;
import com.mascarpone.delivery.service.GeneralService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public interface ShopBranchService extends GeneralService<ShopBranch> {
    Page<ShopBranch> getAllBranches(ShopBranch shopBranch, int page, int size);

    List<ShopBranch> findAllByShopIdAndActiveTrueOrderByAddressAsc(Long shopId);

    Optional<ShopBranch> findByIdAndShopId(Long id, Long shopId);

    ShopBranch getOne(Long id);

    ResponseEntity<?> createOrUpdateShopBranch(ShopBranch branch, UUID shopAdminUuid);

    ResponseEntity<?> getShopBranchesByShopAdmin(Optional<Integer> page, Optional<String> name, UUID shopAdminUuid);

    ResponseEntity<?> getShopBranchByShopAdmin(Long id, UUID shopAdminUuid);

    ResponseEntity<?> getShopBranchesByCustomer(Long shopId);

    ResponseEntity<?> getShopBranchByCustomer(Long shopId, Long id);
}
