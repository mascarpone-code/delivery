package com.mascarpone.delivery.repository.product.specification;

import com.mascarpone.delivery.entity.product.Product;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class ProductSpecification implements Specification<Product> {
    private Product product;

    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (product.getName() != null) {
            predicates.add(cb.like(cb.lower(root.get("name")), "%" + product.getName().toLowerCase() + "%"));
        }

        if (product.getShop() != null) {
            predicates.add(cb.equal(root.get("shop"), product.getShop()));
        }

        if (product.getProductGroup() != null) {
            predicates.add(cb.equal(root.get("productGroup"), product.getProductGroup()));
        }

        return criteriaQuery.where(cb.and(predicates.toArray(new Predicate[0]))).distinct(true).getRestriction();
    }
}
