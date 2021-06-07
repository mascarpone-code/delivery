package com.mascarpone.delivery.payload.auth;

import com.mascarpone.delivery.entity.userrole.UserRole;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class CourierAuthResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private Set<UserRole> roles;
    private String courierName;

    public CourierAuthResponse(String accessToken, Set<UserRole> roles, String courierName) {
        this.accessToken = accessToken;
        this.roles = roles;
        this.courierName = courierName;
    }
}
