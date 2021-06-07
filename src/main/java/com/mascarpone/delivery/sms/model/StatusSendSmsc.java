package com.mascarpone.delivery.sms.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusSendSmsc {
    private String id;
    private String cnt;
    private String error;
    private String error_code;
}
