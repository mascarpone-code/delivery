package com.mascarpone.delivery.payload.supplier;

import com.mascarpone.delivery.entity.supplier.Supplier;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SupplierListResponse {
    private long totalSupplierCount;
    private List<Supplier> suppliers;

    public SupplierListResponse(List<Supplier> suppliers, long count) {
        this.totalSupplierCount = count;
        this.suppliers = suppliers;
    }
}
