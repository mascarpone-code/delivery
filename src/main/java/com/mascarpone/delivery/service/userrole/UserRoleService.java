package com.mascarpone.delivery.service.userrole;

import com.mascarpone.delivery.security.Role;
import com.mascarpone.delivery.entity.userrole.UserRole;
import com.mascarpone.delivery.service.GeneralService;

import java.util.List;
import java.util.Set;

public interface UserRoleService extends GeneralService<UserRole> {
    List<UserRole> findAllByUser_Login(String login);

    Set<UserRole> findAllByRole(Role role);
}
