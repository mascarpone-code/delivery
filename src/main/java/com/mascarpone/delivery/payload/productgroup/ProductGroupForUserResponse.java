package com.mascarpone.delivery.payload.productgroup;

import com.mascarpone.delivery.entity.productgroup.ProductGroup;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductGroupForUserResponse {
    private Long id;
    private String name;

    public ProductGroupForUserResponse(ProductGroup productGroup) {
        this.id = productGroup.getId();
        this.name = productGroup.getName();
    }
}
