package com.mascarpone.delivery.security;

import com.mascarpone.delivery.entity.user.User;
import com.mascarpone.delivery.entity.userrole.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
public class UserPrincipal implements UserDetails {
    private UUID id;
    private String login;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    static UserPrincipal create(User user, List<UserRole> userRoles) {
        var authorities = userRoles.stream()
                .map(userRole -> new SimpleGrantedAuthority(userRole.getRole().name()))
                .collect(Collectors.toList());

        return new UserPrincipal(
                user.getUuid(),
                user.getLogin(),
                user.getPassword(),
                authorities
        );
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String toString() {
        return "UserPrincipal{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", authorities=" + authorities +
                '}';
    }
}
