package com.mascarpone.delivery.payload.nomenclature;

import com.mascarpone.delivery.entity.nomenclature.Nomenclature;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NomenclatureListResponse {
    private long totalSupplierCount;
    private List<Nomenclature> nomenclatures;

    public NomenclatureListResponse(List<Nomenclature> nomenclatures, long count) {
        this.totalSupplierCount = count;
        this.nomenclatures = nomenclatures;

    }
}
