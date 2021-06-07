package com.mascarpone.delivery.payload.socket.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthSocketResponse {
    private String status;
    private Long accountId;

    public AuthSocketResponse(String status, Long accountId) {
        this.status = status;
        this.accountId = accountId;
    }
}
