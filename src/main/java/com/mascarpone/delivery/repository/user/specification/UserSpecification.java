package com.mascarpone.delivery.repository.user.specification;

import com.mascarpone.delivery.entity.user.User;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class UserSpecification implements Specification<User> {
    private final User user;

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (user.getShopPrefix() != null) {
            predicates.add(cb.equal(cb.lower(root.get("shopPrefix")), user.getShopPrefix().toLowerCase()));
        }

        if (user.getPhoneNumber() != null) {
            predicates.add(cb.like(root.get("login"), "%" + user.getPhoneNumber() + "%"));
        }

        if (user.getAccountType() != null) {
            predicates.add(cb.equal(root.get("accountType"), user.getAccountType()));
        }

        if (user.getShop() != null) {
            predicates.add(cb.equal(root.get("shop"), user.getShop()));
        }

        if (user.getName() != null) {
            predicates.add(cb.like(cb.lower(root.get("name")), user.getName().toLowerCase() + "%"));
        }

        if (user.getCourierActive() != null) {
            predicates.add(cb.equal(root.get("courierActive"), user.getCourierActive()));
        }

        if (user.getEnabled() != null) {
            predicates.add(cb.equal(root.get("enabled"), user.getEnabled()));
        }

        return criteriaQuery.where(cb.and(predicates.toArray(new Predicate[0]))).distinct(true).getRestriction();
    }
}
