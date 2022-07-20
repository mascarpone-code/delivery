package com.mascarpone.delivery.pushnotification;

import com.mascarpone.delivery.pushnotification.android.PushNotificatorAndroid;
import com.mascarpone.delivery.pushnotification.apple.PushNotificatorApple;

public class PushNotificationFactory {
    public static PushNotificatorInterface createPushNotificator(PushType pushType){
        return switch (pushType) {
            case APPLE -> new PushNotificatorApple();
            case ANDROID -> new PushNotificatorAndroid();
        };
    }
}
