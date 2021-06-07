package com.mascarpone.delivery.repository.productgroup.specification;

import com.mascarpone.delivery.entity.productgroup.ProductGroup;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class ProductGroupSpecification implements Specification<ProductGroup> {
    private ProductGroup productGroup;

    @Override
    public Predicate toPredicate(Root<ProductGroup> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (productGroup.getShop() != null) {
            predicates.add(cb.equal(root.get("shop"), productGroup.getShop()));
        }

        if (productGroup.getName() != null) {
            predicates.add(cb.like(cb.lower(root.get("name")), "%" + productGroup.getName().toLowerCase() + "%"));
        }

        return criteriaQuery.where(cb.and(predicates.toArray(new Predicate[0]))).distinct(true).getRestriction();
    }
}
