package com.mascarpone.delivery.payload.user;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserForAdminListResponse {
    private long totalCustomersCount;
    private List<UserForAdminResponse> customers;

    public UserForAdminListResponse(long totalCustomersCount, List<UserForAdminResponse> customers) {
        this.totalCustomersCount = totalCustomersCount;
        this.customers = customers;
    }
}
