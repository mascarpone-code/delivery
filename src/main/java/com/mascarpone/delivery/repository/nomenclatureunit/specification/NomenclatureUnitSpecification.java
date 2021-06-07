package com.mascarpone.delivery.repository.nomenclatureunit.specification;

import com.mascarpone.delivery.entity.nomenclatureunit.NomenclatureUnit;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class NomenclatureUnitSpecification implements Specification<NomenclatureUnit> {
    private NomenclatureUnit nomenclatureUnit;

    @Override
    public Predicate toPredicate(Root<NomenclatureUnit> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (nomenclatureUnit.getName() != null) {
            predicates.add(cb.like(cb.lower(root.get("name")), "%" + nomenclatureUnit.getName().toLowerCase() + "%"));
        }

        return criteriaQuery.where(cb.and(predicates.toArray(new Predicate[0]))).distinct(true).getRestriction();
    }
}
