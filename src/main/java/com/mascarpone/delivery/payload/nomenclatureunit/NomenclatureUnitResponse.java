package com.mascarpone.delivery.payload.nomenclatureunit;

import com.mascarpone.delivery.entity.nomenclatureunit.NomenclatureUnit;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class NomenclatureUnitResponse {
    private String name;

    public NomenclatureUnitResponse(NomenclatureUnit unitNomenclature) {
        this.name = unitNomenclature.getName();
    }
}
