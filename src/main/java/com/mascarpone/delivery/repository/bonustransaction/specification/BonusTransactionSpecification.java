package com.mascarpone.delivery.repository.bonustransaction.specification;

import com.mascarpone.delivery.entity.bonustransaction.BonusTransaction;
import com.mascarpone.delivery.entity.bonustransaction.BonusTransactionFilter;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class BonusTransactionSpecification implements Specification<BonusTransaction> {
    private BonusTransactionFilter filter;

    @Override
    public Predicate toPredicate(Root<BonusTransaction> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (filter.getCustomerId() != null) {
            predicates.add(cb.equal(root.get("customer"), filter.getCustomerId()));
        }

        if (filter.getCustomerName() != null) {
            predicates.add(cb.like(cb.lower(root.get("customerName")), filter.getCustomerName().toLowerCase() + "%"));
        }

        if (filter.getCustomerPhoneNumber() != null) {
            predicates.add(cb.like(cb.lower(root.get("customerPhoneNumber")), filter.getCustomerPhoneNumber().toLowerCase() + "%"));
        }

        if (filter.getDateFrom() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("dateCreate"), filter.getDateFrom()));
        }

        if (filter.getDateTo() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("dateCreate"), filter.getDateTo()));
        }

        if (filter.getShopId() != null) {
            predicates.add(cb.equal(root.get("shop"), filter.getShopId()));
        }

        if (filter.getType() != null) {
            predicates.add(cb.equal(root.get("type"), filter.getType()));
        }

        return criteriaQuery.where(cb.and(predicates.toArray(new Predicate[0]))).distinct(true).getRestriction();
    }
}
