package com.mascarpone.delivery.pushnotification.android.retrofit;

import com.mascarpone.delivery.pushnotification.android.request.AndroidPushRequest;
import com.mascarpone.delivery.pushnotification.android.response.AndroidPushResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiPushAndroid {
    @POST("fcm/send")
    Call<AndroidPushResponse> sendPushMessage(@Header("Content-Type") String contentType,
                                              @Header("Authorization") String token,
                                              @Body AndroidPushRequest androidPushRequest);
}
