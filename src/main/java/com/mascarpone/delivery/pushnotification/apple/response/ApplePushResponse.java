package com.mascarpone.delivery.pushnotification.apple.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ApplePushResponse {
    private String canonical_ids;
    private String success;
    private String failure;
    private List<Results> results;
    private String multicast_id;
}
