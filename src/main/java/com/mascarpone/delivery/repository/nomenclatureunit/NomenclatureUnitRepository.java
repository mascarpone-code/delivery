package com.mascarpone.delivery.repository.nomenclatureunit;

import com.mascarpone.delivery.entity.nomenclatureunit.NomenclatureUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface NomenclatureUnitRepository extends JpaRepository<NomenclatureUnit, Long>, JpaSpecificationExecutor<NomenclatureUnit> {
    Optional<NomenclatureUnit> findByName(String s);
}
