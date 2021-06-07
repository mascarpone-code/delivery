package com.mascarpone.delivery.payload.socket.sendmessage;

import com.mascarpone.delivery.payload.order.OrderForCookResponse;
import lombok.Data;

@Data
public class SendMessageToCookResponse {
    private OrderForCookResponse order;
    private String message;
}
