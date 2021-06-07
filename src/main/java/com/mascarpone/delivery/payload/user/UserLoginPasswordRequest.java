package com.mascarpone.delivery.payload.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginPasswordRequest {
    private String login;
    private String password;
    private String secretWord;
}
