package com.mascarpone.delivery.pushnotification.android.request;

import com.mascarpone.delivery.payload.socket.GeneralSocketResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AndroidPushRequest {
    private String to;
    private GeneralSocketResponse data;
}
