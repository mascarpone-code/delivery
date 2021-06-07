package com.mascarpone.delivery.pushnotification.android.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientAndroid {
    private static final String ROOT_URL = "https://fcm.googleapis.com/";

    private static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ApiPushAndroid getApiService() {
        return getRetrofitInstance().create(ApiPushAndroid.class);
    }
}
