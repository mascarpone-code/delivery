package com.mascarpone.delivery.sms;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientSmsc {
    /**
     * Get Retrofit Instance
     */
    private static Retrofit getRetrofitInstance(String url) {
        return new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ApiServerSmsc getApiService(String url) {
        return getRetrofitInstance(url).create(ApiServerSmsc.class);
    }
}
