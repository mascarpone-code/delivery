package com.mascarpone.delivery.repository.modifier.specification;

import com.mascarpone.delivery.entity.modifier.Modifier;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class ModifierSpecification implements Specification<Modifier> {
    private Modifier modifier;

    @Override
    public Predicate toPredicate(Root<Modifier> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (modifier.getName() != null) {
            predicates.add(cb.like(cb.lower(root.get("name")), "%" + modifier.getName().toLowerCase() + "%"));
        }

        if (modifier.getShop() != null) {
            predicates.add(cb.equal(root.get("shop"), modifier.getShop()));
        }

        if (modifier.getCreator() != null) {
            predicates.add(cb.equal(root.get("creator"), modifier.getCreator()));
        }

        return criteriaQuery.where(cb.and(predicates.toArray(new Predicate[0]))).distinct(true).getRestriction();
    }
}
