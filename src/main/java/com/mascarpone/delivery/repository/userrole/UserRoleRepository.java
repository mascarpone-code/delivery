package com.mascarpone.delivery.repository.userrole;

import com.mascarpone.delivery.security.Role;
import com.mascarpone.delivery.entity.userrole.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    List<UserRole> findAllByUser_Login(String login);

    Set<UserRole> findAllByRole(Role role);
}
