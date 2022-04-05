package com.mascarpone.delivery.service.nomenclature;

import com.mascarpone.delivery.entity.nomenclature.Nomenclature;
import com.mascarpone.delivery.service.GeneralService;

import java.util.Optional;

public interface NomenclatureService extends GeneralService<Nomenclature> {
    /**
     * Automatic sending of the order to the supplier
     */
    void getAndSendNomenclatureForOrder();

    Optional<Nomenclature> findById(Long id);

    /**
     * Getting the nomenclature by name
     *
     * @param s - nomenclature name
     * @return nomenclature entity
     */
    Optional<Nomenclature> getByName(String s);
}
