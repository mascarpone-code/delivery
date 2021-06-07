package com.mascarpone.delivery.pushnotification.apple.request;

import com.mascarpone.delivery.payload.socket.GeneralSocketResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplePushRequest {
    private Notification notification;
    private GeneralSocketResponse data;
    private String[] registration_ids;
    private String priority;
}
