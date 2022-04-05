package com.mascarpone.delivery.service.userrole;

import com.mascarpone.delivery.entity.userrole.UserRole;
import com.mascarpone.delivery.repository.userrole.UserRoleRepository;
import com.mascarpone.delivery.security.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserRoleServiceImpl implements UserRoleService {
    private final UserRoleRepository repository;

    @Override
    public List<UserRole> findAllByUser_Login(String login) {
        return repository.findAllByUser_Login(login);
    }

    @Override
    public Set<UserRole> findAllByRole(Role role) {
        return repository.findAllByRole(role);
    }

    @Override
    public List<UserRole> getAll() {
        return repository.findAll();
    }

    @Override
    public void save(UserRole object) {
        repository.save(object);
    }
}
