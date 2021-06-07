package com.mascarpone.delivery.repository.supplier.specification;

import com.mascarpone.delivery.entity.supplier.Supplier;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class SupplierSpecification implements Specification<Supplier> {
    private Supplier supplier;

    @Override
    public Predicate toPredicate(Root<Supplier> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (supplier.getName() != null) {
            predicates.add(cb.like(cb.lower(root.get("name")), "%" + supplier.getName().toLowerCase() + "%"));
        }
        if (supplier.getEmail() != null) {
            predicates.add(cb.like(cb.lower(root.get("email")), "%" + supplier.getEmail().toLowerCase() + "%"));
        }
        if (supplier.getItn() != null) {
            predicates.add(cb.equal(root.get("ITN"), supplier.getItn()));
        }

        predicates.add(cb.equal(root.get("shopId"), supplier.getShopId()));

        return criteriaQuery.where(cb.and(predicates.toArray(new Predicate[0]))).distinct(true).getRestriction();
    }
}
