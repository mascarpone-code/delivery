package com.mascarpone.delivery.pushnotification;

import com.mascarpone.delivery.payload.socket.GeneralSocketResponse;

public interface PushNotificatorInterface {
    void sendPushMessage(String token, GeneralSocketResponse generalSocketResponse, String tokenForPush);
}
