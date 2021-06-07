package com.mascarpone.delivery.service.websocketpush;

import com.mascarpone.delivery.entity.enums.AccountActionType;
import com.mascarpone.delivery.entity.order.UserOrder;
import com.mascarpone.delivery.entity.user.User;

import java.io.IOException;
import java.util.List;

public interface WebSocketPushService {
    void sendMessageToShop(UserOrder order, AccountActionType type) throws IOException;

    void sendMessageToUsers(List<User> users, Object object, String appPushToken) throws IOException;
}
