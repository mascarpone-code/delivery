package com.mascarpone.delivery.service.userpromocode;

import com.mascarpone.delivery.entity.promocode.PromoCode;
import com.mascarpone.delivery.entity.user.User;
import com.mascarpone.delivery.entity.userpromocode.UserPromoCode;
import com.mascarpone.delivery.repository.userpromocode.UserPromoCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserPromoCodeServiceImpl implements UserPromoCodeService {
    private final UserPromoCodeRepository repository;

    @Override
    public List<UserPromoCode> getAll() {
        return repository.findAll();
    }

    @Override
    public void save(UserPromoCode object) {
        repository.save(object);
    }

    @Override
    public Boolean existsByCustomerAndPromoCode(User customer, PromoCode promoCode) {
        return repository.existsByCustomerAndPromoCode(customer, promoCode);
    }
}
