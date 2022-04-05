package com.mascarpone.delivery.ws.sessionpool;

import com.mascarpone.delivery.entity.user.User;
import com.mascarpone.delivery.ws.exception.AuthSocketException;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SocketSessionPool {
    private static final String ACCOUNT_NOT_AUTHORIZE = "Пройдите процедуру авторизации";
    private final Map<WebSocketSession, User> sessions = new ConcurrentHashMap<>();
    private final Map<UUID, Boolean> userOnline = new ConcurrentHashMap<>();

    public void openSession(WebSocketSession session, User account) {
        sessions.put(session, account);
        userOnline.put(account.getUuid(), true);
    }

    public void closeSession(WebSocketSession session, CloseStatus closeStatus) {
        var isOnline = false;

        try {
            var accountId = getSessionAccount((session)).getUuid();

            for (var entry : sessions.entrySet()) {
                if (accountId.equals(entry.getValue().getUuid())) {
                    if (entry.getKey().isOpen()) {
                        isOnline = true;
                    }
                }
            }
        } catch (NullPointerException ignored) {} finally {
            userOnline.put(getSessionAccount((session)).getUuid(), isOnline);
            sessions.remove(session);
        }

        try {
            session.close(closeStatus);
        } catch (IOException ignored) {}
    }

    public User getSessionAccount(WebSocketSession session) {
        return sessions.get(session);
    }

    public Map<WebSocketSession, User> getSessions() {
        return sessions;
    }

    public Map<UUID, Boolean> getUserOnline() {
        return userOnline;
    }

    public void checkAuth(WebSocketSession session) {
        if (getSessionAccount(session) == null) {
            throw new AuthSocketException(ACCOUNT_NOT_AUTHORIZE);
        }
    }
}
