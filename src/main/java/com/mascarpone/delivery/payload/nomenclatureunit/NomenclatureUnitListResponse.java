package com.mascarpone.delivery.payload.nomenclatureunit;

import com.mascarpone.delivery.entity.nomenclatureunit.NomenclatureUnit;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NomenclatureUnitListResponse {
    private long totalSupplierCount;
    private List<NomenclatureUnit> unitNomenclatures;

    public NomenclatureUnitListResponse(List<NomenclatureUnit> unitNomenclatures, long count) {
        this.totalSupplierCount = count;
        this.unitNomenclatures =  unitNomenclatures;

    }
}
