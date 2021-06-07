package com.mascarpone.delivery.ws;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mascarpone.delivery.payload.socket.GeneralSocketError;
import com.mascarpone.delivery.payload.socket.GeneralSocketResponse;
import com.mascarpone.delivery.ws.command.GeneralSocketCommand;
import com.mascarpone.delivery.ws.command.SocketCommandHelper;
import com.mascarpone.delivery.ws.command.impl.AuthSocketCommand;
import com.mascarpone.delivery.ws.exception.AuthSocketException;
import com.mascarpone.delivery.ws.exception.BadDataSocketException;
import com.mascarpone.delivery.ws.exception.GeneralSocketException;
import com.mascarpone.delivery.ws.exception.UnknownActionException;
import com.mascarpone.delivery.ws.sessionpool.SocketSessionPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

@Slf4j
@Component
public class SocketHandler extends TextWebSocketHandler {
    @Autowired
    private SocketCommandHelper socketCommandHelper;
    @Autowired
    private SocketSessionPool socketSessionPool;
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        GeneralSocketCommand socketCommand = null;

        try {
            socketCommand = socketCommandHelper.getCommand(message);

            if (!(socketCommand instanceof AuthSocketCommand)) {
                socketSessionPool.checkAuth(session);
            }

            GeneralSocketResponse result = socketCommand.execute(session, message);

            sendMessage(session, result);
        } catch (GeneralSocketException ex) {
            log.error(ex.getMessage(), ex.getCause());
            sendMessage(session, new GeneralSocketError(socketCommand.getActionType(), ex.getMessage()));
        } catch (UnknownActionException | BadDataSocketException ex) {
            log.error(ex.getMessage(), ex.getCause());
            sendMessage(session, new GeneralSocketError(null, ex.getMessage()));
        } catch (AuthSocketException ex) {
            log.error(ex.getMessage(), ex.getCause());
            sendMessage(session, new GeneralSocketError(null, ex.getMessage()));
            socketSessionPool.closeSession(session, CloseStatus.NORMAL);
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable throwable) {
        log.error(throwable.getMessage(), throwable.getCause());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        socketSessionPool.closeSession(session, CloseStatus.NORMAL);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
    }

    private void sendMessage(WebSocketSession session, Object object) throws IOException {
        if (session.isOpen()) {
            session.sendMessage(new TextMessage(mapper.writeValueAsString(object)));
        }
    }
}
