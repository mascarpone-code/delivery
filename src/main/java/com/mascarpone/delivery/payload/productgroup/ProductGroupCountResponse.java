package com.mascarpone.delivery.payload.productgroup;

import com.mascarpone.delivery.entity.productgroup.ProductGroup;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductGroupCountResponse {
    private long totalProductGroupCount;
    private List<ProductGroup> productGroups;

    public ProductGroupCountResponse(List<ProductGroup> productGroups, long count) {
        totalProductGroupCount = count;
        this.productGroups = productGroups;
    }
}
