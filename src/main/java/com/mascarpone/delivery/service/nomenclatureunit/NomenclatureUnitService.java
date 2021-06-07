package com.mascarpone.delivery.service.nomenclatureunit;

import com.mascarpone.delivery.entity.nomenclatureunit.NomenclatureUnit;
import com.mascarpone.delivery.service.GeneralService;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface NomenclatureUnitService extends GeneralService<NomenclatureUnit> {
    Page<NomenclatureUnit> findAllPageable(NomenclatureUnit filter, int page, int size);

    Optional<NomenclatureUnit> getByName(String s);

    NomenclatureUnit getOne(Long id);
}
