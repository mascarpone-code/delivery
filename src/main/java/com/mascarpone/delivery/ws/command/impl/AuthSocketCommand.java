package com.mascarpone.delivery.ws.command.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mascarpone.delivery.entity.enums.AccountActionType;
import com.mascarpone.delivery.payload.socket.GeneralSocketRequest;
import com.mascarpone.delivery.payload.socket.GeneralSocketResponse;
import com.mascarpone.delivery.payload.socket.auth.AuthSocketRequest;
import com.mascarpone.delivery.payload.socket.auth.AuthSocketResponse;
import com.mascarpone.delivery.security.TokenProvider;
import com.mascarpone.delivery.service.user.UserService;
import com.mascarpone.delivery.ws.command.GeneralSocketCommand;
import com.mascarpone.delivery.ws.exception.AuthSocketException;
import com.mascarpone.delivery.ws.sessionpool.SocketSessionPool;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Component
@Qualifier(value = "auth")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthSocketCommand implements GeneralSocketCommand {
    private final ObjectMapper mapper = new ObjectMapper();
    private final SocketSessionPool socketSessionPool;
    private final UserService userService;
    private final TokenProvider tokenProvider;

    @Override
    public AccountActionType getActionType() {
        return AccountActionType.AUTH;
    }

    @Override
    public GeneralSocketResponse execute(WebSocketSession session, TextMessage message) {
        try {
            var request = mapper.readValue(message.getPayload(),
                    new TypeReference<GeneralSocketRequest<AuthSocketRequest>>() {
                    });

            var authSocketRequest = request.getObject();
            var authSocketResponse = new AuthSocketResponse("ERROR", null);

            if (tokenProvider.validateToken(authSocketRequest.getAccessToken())) {
                var userId = tokenProvider.getUserIdFromToken(authSocketRequest.getAccessToken());

                if (userService.findById(userId).isPresent()) {
                    var account = userService.findById(userId).get();
                    socketSessionPool.openSession(session, account);
                    authSocketResponse.setStatus("OK");
                    authSocketResponse.setAccountId(account.getId());
                } else {
                    throw new AuthSocketException("AUTHORIZATION_ERROR");
                }
            } else {
                throw new AuthSocketException("AUTHORIZATION_ERROR");
            }

            return new GeneralSocketResponse<>(getActionType(), authSocketResponse);
        } catch (Exception ex) {
            throw new AuthSocketException("AUTHORIZATION_ERROR", ex);
        }
    }
}
