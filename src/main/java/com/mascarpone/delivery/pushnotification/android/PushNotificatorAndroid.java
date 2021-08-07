package com.mascarpone.delivery.pushnotification.android;

import com.mascarpone.delivery.payload.socket.GeneralSocketResponse;
import com.mascarpone.delivery.pushnotification.PushNotificatorInterface;
import com.mascarpone.delivery.pushnotification.android.request.AndroidPushRequest;
import com.mascarpone.delivery.pushnotification.android.response.AndroidPushResponse;
import com.mascarpone.delivery.pushnotification.android.retrofit.ApiPushAndroid;
import com.mascarpone.delivery.pushnotification.android.retrofit.RetrofitClientAndroid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PushNotificatorAndroid implements PushNotificatorInterface {
    Logger logger = LoggerFactory.getLogger(PushNotificatorAndroid.class);
    private final ApiPushAndroid apiPushAndroid = RetrofitClientAndroid.getApiService();

    @Override
    public void sendPushMessage(String token, GeneralSocketResponse generalSocketResponse, String tokenForPush) {
        var androidPushRequest = new AndroidPushRequest();
        androidPushRequest.setTo(token);
        androidPushRequest.setData(generalSocketResponse);

        var requestFireBasePush = apiPushAndroid.sendPushMessage(
                "application/json", tokenForPush, androidPushRequest);

        requestFireBasePush.enqueue(new Callback<AndroidPushResponse>() {
            @Override
            public void onResponse(Call<AndroidPushResponse> call, Response<AndroidPushResponse> response) {
            }

            @Override
            public void onFailure(Call<AndroidPushResponse> call, Throwable throwable) {
                logger.error("throwable push android " + throwable);
            }
        });
    }
}
