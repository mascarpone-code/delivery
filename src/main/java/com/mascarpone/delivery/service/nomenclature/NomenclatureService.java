package com.mascarpone.delivery.service.nomenclature;

import com.mascarpone.delivery.entity.nomenclature.Nomenclature;
import com.mascarpone.delivery.entity.shopbranch.ShopBranch;
import com.mascarpone.delivery.service.GeneralService;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface NomenclatureService extends GeneralService<Nomenclature> {
    Page<Nomenclature> findAllWithFilterPageable(Nomenclature filter, int page, int size);

    void getAndSendNomenclatureForOrder();

    void getAndSendNomenclatureByShopBranchId(ShopBranch shopBranch);

    Optional<Nomenclature> getByName(String s);
}
