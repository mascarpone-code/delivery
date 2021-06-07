package com.mascarpone.delivery.payload.nomenclature;

import com.mascarpone.delivery.entity.nomenclature.Nomenclature;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NomenclatureResponse {

    private String name;
    private Double quantity;
    private Long unitNomenclatureId;

    public NomenclatureResponse(Nomenclature nomenclature) {
        this.name = nomenclature.getName();
        this.quantity = nomenclature.getQuantity();
        this.unitNomenclatureId = nomenclature.getNomenclatureUnit().getId();
    }
}
