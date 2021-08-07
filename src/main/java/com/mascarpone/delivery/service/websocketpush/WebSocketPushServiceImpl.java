package com.mascarpone.delivery.service.websocketpush;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mascarpone.delivery.entity.enums.AccountActionType;
import com.mascarpone.delivery.security.Role;
import com.mascarpone.delivery.entity.order.UserOrder;
import com.mascarpone.delivery.entity.pushtoken.PushToken;
import com.mascarpone.delivery.entity.user.User;
import com.mascarpone.delivery.entity.userrole.UserRole;
import com.mascarpone.delivery.payload.socket.GeneralSocketResponse;
import com.mascarpone.delivery.payload.socket.neworder.NewOrderSocketRequest;
import com.mascarpone.delivery.pushnotification.PushNotificationFactory;
import com.mascarpone.delivery.service.userrole.UserRoleService;
import com.mascarpone.delivery.ws.sessionpool.SocketSessionPool;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WebSocketPushServiceImpl implements WebSocketPushService {
    private final SocketSessionPool socketSessionPool;
    private final UserRoleService userRoleService;
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void sendMessageToShop(UserOrder order, AccountActionType type) throws IOException {
        var sessions = socketSessionPool.getSessions();
        var request = new GeneralSocketResponse<>(type, new NewOrderSocketRequest(order));

        for (var entry : sessions.entrySet()) {
            if (order.getShop().getId().equals(entry.getValue().getShop().getId())) {
                var userRoles = userRoleService.findAllByRole(Role.SHOP);

                for (var userRole : userRoles) {
                    if (entry.getValue().getId().equals(userRole.getUser().getId())) {
                        if (entry.getKey().isOpen()) {
                            entry.getKey().sendMessage(new TextMessage(mapper.writeValueAsString(request)));
                        }
                    }
                }
            }
        }
    }

    @Override
    public void sendMessageToUsers(List<User> users, Object object, String appPushToken) throws IOException {
        for (var user : users) {
            var isOnline = false;
            var sessions = socketSessionPool.getSessions();

            for (var entry : sessions.entrySet()) {
                if (user.getId().equals(entry.getValue().getId())) {
                    if (entry.getKey().isOpen()) {
                        isOnline = true;
                        entry.getKey().sendMessage(new TextMessage(mapper.writeValueAsString(object)));
                    }
                }
            }

            if (!isOnline) {
                var pushTokens = user.getPushTokens();

                for (var pushToken : pushTokens) {
                    var generalSocketResponse = (GeneralSocketResponse) object;
                    PushNotificationFactory.createPushNotificator(
                            pushToken.getDevice()).sendPushMessage(pushToken.getToken(),
                            generalSocketResponse,
                            appPushToken);
                }
            }
        }
    }
}
