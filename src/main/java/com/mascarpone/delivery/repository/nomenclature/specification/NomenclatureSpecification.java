package com.mascarpone.delivery.repository.nomenclature.specification;

import com.mascarpone.delivery.entity.nomenclature.Nomenclature;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class NomenclatureSpecification implements Specification<Nomenclature> {
    private Nomenclature nomenclature;

    @Override
    public Predicate toPredicate(Root<Nomenclature> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (nomenclature.getName() != null) {
            predicates.add(cb.like(cb.lower(root.get("name")), "%" + nomenclature.getName().toLowerCase() + "%"));
        }

        predicates.add(cb.equal(root.get("shopId"), nomenclature.getShopId()));

        return criteriaQuery.where(cb.and(predicates.toArray(new Predicate[0]))).distinct(true).getRestriction();
    }
}
