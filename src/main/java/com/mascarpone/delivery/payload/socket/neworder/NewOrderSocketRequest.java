package com.mascarpone.delivery.payload.socket.neworder;

import com.mascarpone.delivery.entity.order.UserOrder;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewOrderSocketRequest {
    private UserOrder order;
}
