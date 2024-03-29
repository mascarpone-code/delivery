package com.mascarpone.delivery.exception;

public class ExceptionConstants {
    public static final String USER_ALREADY_EXISTS = "This login is already registered in the system";
    public static final String TOO_FREQUENT_CODE_REQUEST = "Too frequent code request";
    public static final String ATTEMPTS_EXHAUSTED = "Attempts exhausted. Request new sms code";
    public static final String TIME_IS_UP = "Time's up. Request new sms code";
    public static final String WRONG_SMS_CODE = "Wrong code";
    public static final String INVALID_REQUEST_FORMAT = "Invalid request format";
    public static final String USER_NOT_FOUND = "User not found";
    public static final String USER_BLOCKED = "Your account is blocked by the administrator";
    public static final String NO_ACCESS = "No access";
    public static final String NO_ACTIVE_ADDRESS = "No active address";
    public static final String WRONG_ORDINAL_NUMBER = "Wrong ordinal number";
    public static final String MODIFIER_NOT_FOUND = "Modifier not found";
    public static final String GROUP_NOT_FOUND = "Product group not found";
    public static final String PRODUCT_NOT_FOUND = "Product not found";
    public static final String PHOTO_NOT_FOUND = "Photo not found";
    public static final String PRODUCT_GROUP_NOT_EMPTY = "Product group is not empty";
    public static final String SHOP_NOT_FOUND = "Shop not found";
    public static final String UNIT_NOT_FOUND = "Unit not found";
    public static final String ORDER_IS_EMPTY = "Заказ пуст";
    public static final String NO_COUNT = "Quantity not specified";
    public static final String NOT_ENOUGH_BONUSES = "Not enough bonuses to pay";
    public static final String OUT_OFF_BONUS_LIMIT = "Bonus payment limit exceeded";
    public static final String PROMOCODE_ALREADY_USED = "Promo code already used";
    public static final String PROMOCODE_NOT_FOUND = "Promo code not found";
    public static final String PROMOCODE_EXPIRED = "Promo code is invalid";
    public static final String PROMOCODE_EXISTS = "This promo code already exists";
    public static final String BRANCH_NOT_FOUND = "Shop branch not found";
    public static final String NO_DELIVERY_ADDRESS = "Specify the delivery address";
    public static final String DELIVERY_AREA_NOT_FOUND = "Delivery area not found";
    public static final String NOT_ORDER_TIME = "Shop is closed";
    public static final String NO_DELIVERY_AREA = "Address outside the delivery area";
    public static final String LOW_ORDER_PRICE = "The cost of the order is less than the minimum cost";
    public static final String ADDRESS_NOT_FOUND = "Delivery address not found";
    public static final String NOMENCLATURE_FOR_ORDER_NOT_FOUND = "Nomenclatures for order not found";
    public static final String DEFINE_SHOP_BRANCH = "Define shop branch";
    public static final String ORDER_TYPE_ERROR = "Choose \"delivery\" or \"pick up\"";
}
