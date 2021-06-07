package com.mascarpone.delivery.payload.socket.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthSocketRequest {
    private String accessToken;
}
