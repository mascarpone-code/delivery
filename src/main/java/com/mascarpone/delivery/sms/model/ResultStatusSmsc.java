package com.mascarpone.delivery.sms.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultStatusSmsc {
    private String region;
    private String phone;
    private String status;
    private String send_date;
    private String send_timestamp;
    private String type;
    private String last_timestamp;
    private String cost;
    private String operator;
    private String country;
    private String message;
    private String operator_orig;
    private String status_name;
    private String sender_id;
    private String mccmnc;
    private String last_date;
    private String error;
    private String error_code;

    @Override
    public String toString()
    {
        return "ClassPojo [region = "+region+", phone = "+phone+", status = "+status+", send_date = "+send_date+", send_timestamp = "+send_timestamp+", type = "+type+", last_timestamp = "+last_timestamp+", cost = "+cost+", operator = "+operator+", country = "+country+", message = "+message+", operator_orig = "+operator_orig+", status_name = "+status_name+", sender_id = "+sender_id+", mccmnc = "+mccmnc+", last_date = "+last_date+"]";
    }
}
