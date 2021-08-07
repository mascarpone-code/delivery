package com.mascarpone.delivery.utils;

public class Constants {
    public static final String PHONE_NUMBER_PATTERN = "^79\\d{9}$";
    public static final String SECRET_WORD = "11228888";
    public static final String SECRET_PHONE_NUMBER = "79138981234";
    public static final String SECRET_SMS_CODE = "4979";
    public static final Long AUTH_TOKEN_EXPIRATION = 31536000000L;
    public static final String TOKEN_SECRET = "securesecuresecuresecuresecuresecuresecuresecuresecuresecuresecuresecure";
    public static final Integer DEFAULT_PAGE = 0;
    public static final Long DEFAULT_ORDER_NUMBER = 0L;
    public static final Integer FETCH_RECORD_COUNT = 25;
    public static final String GET_PRODUCT_PHOTO_ENDPOINT = "/api/product/photo/";
    public static final Long MILLIS_IN_DAY = 86400000L;
    public static final Long MILLIS_IN_12_HOURS = 43200000L;
    public static final String COOK_NEW_ORDER_MESSAGE = "New order received!";
    public static final Integer MAX_IMAGE_SIDE_SIZE = 800;
    public static final Integer CUSTOMER_PUSH_TOKEN_MAX_COUNT = 3;
}
