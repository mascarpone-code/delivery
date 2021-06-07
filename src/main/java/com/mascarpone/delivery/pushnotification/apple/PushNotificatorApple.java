package com.mascarpone.delivery.pushnotification.apple;

import com.mascarpone.delivery.entity.enums.AccountActionType;
import com.mascarpone.delivery.payload.socket.GeneralSocketResponse;
import com.mascarpone.delivery.payload.socket.sendmessage.SendMessageResponse;
import com.mascarpone.delivery.payload.socket.sendmessage.SimpleMessageResponse;
import com.mascarpone.delivery.pushnotification.PushNotificatorInterface;
import com.mascarpone.delivery.pushnotification.apple.request.ApplePushRequest;
import com.mascarpone.delivery.pushnotification.apple.request.Notification;
import com.mascarpone.delivery.pushnotification.apple.response.ApplePushResponse;
import com.mascarpone.delivery.pushnotification.apple.retrofit.ApiPushApple;
import com.mascarpone.delivery.pushnotification.apple.retrofit.RetrofitClientApple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PushNotificatorApple implements PushNotificatorInterface {
    private final ApiPushApple apiPushApple = RetrofitClientApple.getApiService();
    Logger logger = LoggerFactory.getLogger(PushNotificatorApple.class);

    @Override
    public void sendPushMessage(String token, GeneralSocketResponse generalSocketResponse, String tokenForPush) {
        ApplePushRequest applePushRequest = new ApplePushRequest();

        String[] tokenIds = {token};
        applePushRequest.setRegistration_ids(tokenIds);
        applePushRequest.setData(generalSocketResponse);
        applePushRequest.setPriority("high");
        Notification notification = new Notification();
        notification.setSound("default");

        if (generalSocketResponse.getAccountActionType().equals(AccountActionType.NEWORDER)) {
            SendMessageResponse sendMessageResponse = (SendMessageResponse) generalSocketResponse.getResult();
            notification.setBody(sendMessageResponse.getMessage());
            notification.setTitle("Поступил новый заказ");
        } else if (generalSocketResponse.getAccountActionType().equals(AccountActionType.ORDERFORMED)) {
            SendMessageResponse sendMessageResponse = (SendMessageResponse) generalSocketResponse.getResult();
            notification.setBody(sendMessageResponse.getMessage());
            notification.setTitle("Заказ оформлен");
        } else if (generalSocketResponse.getAccountActionType().equals(AccountActionType.ORDERPREPARING)) {
            SendMessageResponse sendMessageResponse = (SendMessageResponse) generalSocketResponse.getResult();
            notification.setBody(sendMessageResponse.getMessage());
            notification.setTitle("Заказ готовится");
        } else if (generalSocketResponse.getAccountActionType().equals(AccountActionType.ORDERDELIVERING)) {
            SendMessageResponse sendMessageResponse = (SendMessageResponse) generalSocketResponse.getResult();
            notification.setBody(sendMessageResponse.getMessage());
            notification.setTitle("Заказ готов");
        } else if (generalSocketResponse.getAccountActionType().equals(AccountActionType.ORDERDELIVERED)) {
            SendMessageResponse sendMessageResponse = (SendMessageResponse) generalSocketResponse.getResult();
            notification.setBody(sendMessageResponse.getMessage());
            notification.setTitle("Заказ доставлен");
        } else if (generalSocketResponse.getAccountActionType().equals(AccountActionType.ORDERCANCELED)) {
            SendMessageResponse sendMessageResponse = (SendMessageResponse) generalSocketResponse.getResult();
            notification.setBody(sendMessageResponse.getMessage());
            notification.setTitle("Заказ отменён");
        } else if (generalSocketResponse.getAccountActionType().equals(AccountActionType.FLAMPMESSAGE)) {
            SimpleMessageResponse simpleMessageResponse = (SimpleMessageResponse) generalSocketResponse.getResult();
            notification.setBody(simpleMessageResponse.getMessage());
            notification.setTitle("Flamp");
        }

        applePushRequest.setNotification(notification);

        Call<ApplePushResponse> applePushResponseCall = apiPushApple.sendPushMessage(
                "application/json", tokenForPush, applePushRequest);

        applePushResponseCall.enqueue(new Callback<ApplePushResponse>() {
            @Override
            public void onResponse(Call<ApplePushResponse> call, Response<ApplePushResponse> response) {
                //System.out.println("response" + response.body().getSuccess());
            }

            @Override
            public void onFailure(Call<ApplePushResponse> call, Throwable throwable) {
                logger.error("throwable push apple " + throwable);
            }
        });
    }
}
