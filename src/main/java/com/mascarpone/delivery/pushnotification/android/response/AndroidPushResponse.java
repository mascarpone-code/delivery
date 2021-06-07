package com.mascarpone.delivery.pushnotification.android.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AndroidPushResponse {
    private String failure;
    private List<Results> results;
    private String success;
    private String multicast_id;
    private String canonical_ids;
}
