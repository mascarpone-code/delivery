package com.mascarpone.delivery.payload.socket.sendmessage;

import lombok.*;

@Data
public class SendMessageResponse {
    private Long orderId;
    private String message;
}
