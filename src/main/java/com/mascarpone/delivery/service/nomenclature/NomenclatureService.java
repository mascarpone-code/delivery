package com.mascarpone.delivery.service.nomenclature;

import com.mascarpone.delivery.entity.nomenclature.Nomenclature;
import com.mascarpone.delivery.entity.shopbranch.ShopBranch;
import com.mascarpone.delivery.service.GeneralService;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface NomenclatureService extends GeneralService<Nomenclature> {
    /**
     * Automatic sending of the order to the supplier
     */
    void getAndSendNomenclatureForOrder();

    /**
     * Getting the nomenclature by name
     *
     * @param s - nomenclature name
     * @return nomenclature entity
     */
    Optional<Nomenclature> getByName(String s);
}
