package com.mascarpone.delivery.service.nomenclatureunit;

import com.mascarpone.delivery.entity.nomenclatureunit.NomenclatureUnit;
import com.mascarpone.delivery.service.GeneralService;

import java.util.Optional;

public interface NomenclatureUnitService extends GeneralService<NomenclatureUnit> {
    /**
     * Getting the nomenclature unit by name
     *
     * @param s - name of nomenclature unit
     * @return nomenclature unit entity
     */
    Optional<NomenclatureUnit> getByName(String s);

    /**
     * Getting the nomenclature unit by id
     *
     * @param id nomenclature unit id
     * @return nomenclature unit entity
     */
    NomenclatureUnit getOne(Long id);
}
