package com.mascarpone.delivery.pushnotification.apple.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Notification {
    private String title;
    private String body;
    private String sound;
}
