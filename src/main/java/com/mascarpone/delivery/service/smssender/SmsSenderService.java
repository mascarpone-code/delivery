package com.mascarpone.delivery.service.smssender;

public interface SmsSenderService {
    void sendSms(String phoneNumber, String code);
}
