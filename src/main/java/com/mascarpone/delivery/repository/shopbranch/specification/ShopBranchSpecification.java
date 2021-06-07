package com.mascarpone.delivery.repository.shopbranch.specification;

import com.mascarpone.delivery.entity.shopbranch.ShopBranch;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class ShopBranchSpecification implements Specification<ShopBranch> {
    private ShopBranch shopBranch;

    @Override
    public Predicate toPredicate(Root<ShopBranch> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (shopBranch.getName() != null) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + shopBranch.getName().toLowerCase() + "%"));
        }

        if (shopBranch.getShop() != null) {
            predicates.add(criteriaBuilder.equal(root.get("shop"), shopBranch.getShop()));
        }

        return criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0]))).distinct(true).getRestriction();
    }
}
