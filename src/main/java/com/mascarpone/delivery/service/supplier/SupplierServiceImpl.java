package com.mascarpone.delivery.service.supplier;

import com.mascarpone.delivery.entity.supplier.Supplier;
import com.mascarpone.delivery.repository.supplier.SupplierRepository;
import com.mascarpone.delivery.repository.supplier.specification.SupplierSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SupplierServiceImpl implements SupplierService {
    private final SupplierRepository supplierRepository;

    @Override
    public List<Supplier> getAll() {
        return supplierRepository.findAll();
    }

    @Override
    public void save(Supplier supplier) {
        supplierRepository.save(supplier);
    }

    @Override
    public Page<Supplier> findAllWithFilterPageable(Supplier filter, int page, int size) {
        var specification = Specification.where(new SupplierSpecification(filter));

        return supplierRepository.findAll(specification, PageRequest.of(page, size, Sort.Direction.ASC, "name"));
    }

    @Override
    public Supplier getOne(Long id) {
        return supplierRepository.getOne(id);
    }
}
