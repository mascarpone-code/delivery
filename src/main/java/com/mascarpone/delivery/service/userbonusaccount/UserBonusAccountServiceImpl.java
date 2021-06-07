package com.mascarpone.delivery.service.userbonusaccount;

import com.mascarpone.delivery.entity.userbonusaccount.UserBonusAccount;
import com.mascarpone.delivery.repository.userbonusaccount.UserBonusAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserBonusAccountServiceImpl implements UserBonusAccountService {
    private final UserBonusAccountRepository repository;

    @Override
    public List<UserBonusAccount> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<UserBonusAccount> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void save(UserBonusAccount object) {
        repository.save(object);
    }
}
