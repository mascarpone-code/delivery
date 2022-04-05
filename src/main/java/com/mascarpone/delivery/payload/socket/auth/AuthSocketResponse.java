package com.mascarpone.delivery.payload.socket.auth;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AuthSocketResponse {
    private String status;
    private UUID accountUuid;

    public AuthSocketResponse(String status, UUID accountUuid) {
        this.status = status;
        this.accountUuid = accountUuid;
    }
}
