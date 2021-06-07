package com.mascarpone.delivery.ws.command;

import com.mascarpone.delivery.entity.enums.AccountActionType;
import com.mascarpone.delivery.payload.socket.GeneralSocketResponse;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

public interface GeneralSocketCommand {
    AccountActionType getActionType();
    GeneralSocketResponse execute(WebSocketSession session, TextMessage message);
}
