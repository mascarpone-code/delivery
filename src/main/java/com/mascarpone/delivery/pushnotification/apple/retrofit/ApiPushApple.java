package com.mascarpone.delivery.pushnotification.apple.retrofit;

import com.mascarpone.delivery.pushnotification.apple.request.ApplePushRequest;
import com.mascarpone.delivery.pushnotification.apple.response.ApplePushResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiPushApple {
    @POST("fcm/send")
    Call<ApplePushResponse> sendPushMessage(@Header("Content-Type") String contentType,
                                            @Header("Authorization") String token,
                                            @Body ApplePushRequest applePushRequest);
}
