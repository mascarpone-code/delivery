package com.mascarpone.delivery.service.smssender;

import com.mascarpone.delivery.sms.RetrofitClientSmsc;
import com.mascarpone.delivery.sms.model.StatusSendSmsc;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.annotation.Resource;
import java.util.Objects;

@Service
public class SmsSenderServiceImpl implements SmsSenderService {
    @Resource
    private Environment env;

    @Override
    public void sendSms(String phoneNumber, String code) {
        try {
            var apiServerSMS = RetrofitClientSmsc.getApiService(env.getRequiredProperty("sms.url"));

            var SMSC_CHARSET = "utf-8";
            var sendSmsCall = apiServerSMS.sendSms(
                    env.getRequiredProperty("sms.login"),
                    env.getRequiredProperty("sms.password"),
                    phoneNumber,
                    code,
                    "3",
                    "2",
                    SMSC_CHARSET);
            sendSmsCall.enqueue(new Callback<StatusSendSmsc>() {
                @Override
                public void onResponse(Call<StatusSendSmsc> call, Response<StatusSendSmsc> response) {
                    if (response.isSuccessful()) {
                        Objects.requireNonNull(response.body());
                    }
                }

                @Override
                public void onFailure(Call<StatusSendSmsc> call, Throwable throwable) {
                }
            });
        } catch (Exception ignored) {
        }
    }
}
