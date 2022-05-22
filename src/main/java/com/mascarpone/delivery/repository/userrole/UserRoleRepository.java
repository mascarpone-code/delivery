package com.mascarpone.delivery.repository.userrole;

import com.mascarpone.delivery.entity.userrole.UserRole;
import com.mascarpone.delivery.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    List<UserRole> findAllByUser_Login(String login);

    Set<UserRole> findAllByRole(Role role);
}
