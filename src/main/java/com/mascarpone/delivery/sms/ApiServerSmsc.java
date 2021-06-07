package com.mascarpone.delivery.sms;

import com.mascarpone.delivery.sms.model.ResultStatusSmsc;
import com.mascarpone.delivery.sms.model.StatusSendSmsc;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiServerSmsc {
    //---------------------------------------SMSC.RU-------------------------------------------------------------

    @GET("/sys/status.php")
    Call<ResultStatusSmsc> getResult(@Query("login") String login,
                                     @Query("psw") String psw,
                                     @Query("phone") String phone,
                                     @Query("id") String id,
                                     @Query("fmt") String fmt,
                                     @Query("all") String all);

    @GET("/sys/send.php")
    Call<StatusSendSmsc> sendSms(@Query("login") String login,
                                 @Query("psw") String psw,
                                 @Query("phones") String phones,
                                 @Query("mes") String code,
                                 @Query("fmt") String fmt,
                                 @Query("all") String all,
                                 @Query("charset") String charset);
}
