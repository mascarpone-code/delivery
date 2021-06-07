package com.mascarpone.delivery.pushnotification;

import com.mascarpone.delivery.pushnotification.android.PushNotificatorAndroid;
import com.mascarpone.delivery.pushnotification.apple.PushNotificatorApple;

public class PushNotificationFactory {
    public static PushNotificatorInterface createPushNotificator(PushType pushType){
        switch(pushType) {
            case APPLE:
                return new PushNotificatorApple();
            case ANDROID:
                return new PushNotificatorAndroid();
            default:
                throw new RuntimeException();
        }
    }
}
