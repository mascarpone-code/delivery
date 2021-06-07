package com.mascarpone.delivery.security;

import com.mascarpone.delivery.entity.user.User;
import com.mascarpone.delivery.entity.userrole.UserRole;
import com.mascarpone.delivery.service.user.UserService;
import com.mascarpone.delivery.service.userrole.UserRoleService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;
    private final UserRoleService userRoleService;

    public CustomUserDetailsService(UserService userService, UserRoleService userRoleService) {
        this.userService = userService;
        this.userRoleService = userRoleService;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        if (!userService.existsByLogin(login)) {
            throw new UsernameNotFoundException("User not found with login : " + login);
        }

        User user = userService.findByLogin(login);
        List<UserRole> userRoles = userRoleService.findAllByUser_Login(user.getLogin());
        return UserPrincipal.create(user, userRoles);
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userService.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id : " + id));
        List<UserRole> userRoles = userRoleService.findAllByUser_Login(user.getLogin());

        return UserPrincipal.create(user, userRoles);
    }
}
