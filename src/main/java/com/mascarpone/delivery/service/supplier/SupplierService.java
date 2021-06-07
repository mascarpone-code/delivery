package com.mascarpone.delivery.service.supplier;

import com.mascarpone.delivery.entity.supplier.Supplier;
import com.mascarpone.delivery.service.GeneralService;
import org.springframework.data.domain.Page;

public interface SupplierService extends GeneralService<Supplier> {
    Page<Supplier> findAllWithFilterPageable(Supplier filter, int page, int size);

    Supplier getOne(Long id);
}
