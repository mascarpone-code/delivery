package com.mascarpone.delivery.pushnotification.apple.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientApple {
    private static final String ROOT_URL = "https://fcm.googleapis.com/";

    /**
     * Get Retrofit Instance
     */
    private static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ApiPushApple getApiService() {
        return getRetrofitInstance().create(ApiPushApple.class);
    }
}
