package com.mascarpone.delivery.payload.auth;

import com.mascarpone.delivery.entity.userrole.UserRole;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class AuthResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private Set<UserRole> roles;

    public AuthResponse(String accessToken, Set<UserRole> roles) {
        this.accessToken = accessToken;
        this.roles = roles;
    }
}
