package com.mascarpone.delivery.payload.shop;

import com.mascarpone.delivery.entity.shop.Shop;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static com.mascarpone.delivery.utils.Constants.MILLIS_IN_DAY;

@Getter
@Setter
public class ShopForUserResponse {
    private Long id;
    private String name;
    private String prefix;
    private boolean isDelivery;
    private boolean isPickup;
    private boolean isBonusSystem;
    private BigDecimal welcomeBonusAmount;
    private BigDecimal orderAmountPercent;
    private BigDecimal bonusPayAmount;
    private String orderBeginTime;
    private String orderEndTime;
    private Boolean orderTime;
    private Long orderTimeLeft;
    private Boolean shopOpen;
    private String phoneNumber;
    private boolean terminalPayment;
    private boolean cashPayment;
    private Integer orderPreparationTime;
    private boolean popupActive;
    private String popupPath;
    private String popupText;

    public ShopForUserResponse(Shop shop) {
        id = shop.getId();
        name = shop.getName();
        prefix = shop.getPrefix();
        isDelivery = shop.isDelivery();
        isPickup = shop.isPickup();
        isBonusSystem = shop.isBonusSystem();
        welcomeBonusAmount = shop.getWelcomeBonusAmount();
        orderAmountPercent = shop.getOrderAmountPercent();
        bonusPayAmount = shop.getBonusPayAmount();
        orderBeginTime = shop.getOrderBeginTime();
        orderEndTime = shop.getOrderEndTime();

        if (shop.getOrderBeginTime() != null && shop.getOrderEndTime() != null
                && !(shop.getOrderBeginTime().equals(shop.getOrderEndTime()))) {
            Calendar calendarNow = Calendar.getInstance();
            calendarNow.setTime(new Date());

            if (shop.getTimeZone() != null) {
                calendarNow.setTimeZone(TimeZone.getTimeZone(shop.getTimeZone()));
            }

            int hourNow = calendarNow.get(Calendar.HOUR_OF_DAY);
            int minuteNow = calendarNow.get(Calendar.MINUTE);

            int hourBegin = Integer.parseInt(shop.getOrderBeginTime().substring(0, 2));
            int minuteBegin = Integer.parseInt(shop.getOrderBeginTime().substring(3, 5));

            int hourEnd = Integer.parseInt(shop.getOrderEndTime().substring(0, 2));
            int minuteEnd = Integer.parseInt(shop.getOrderEndTime().substring(3, 5));

            boolean a = hourNow > hourEnd;
            boolean b = hourNow < hourBegin;
            boolean c = (hourNow == hourBegin) && (minuteNow < minuteBegin);
            boolean d = (hourNow == hourEnd) && (minuteNow >= minuteEnd);

            long endTime = Time.valueOf(shop.getOrderEndTime() + ":00").getTime();
            long nowTime = Time.valueOf(hourNow + ":" + minuteNow + ":00").getTime();

            if (hourBegin <= hourEnd) {
                orderTime = !a && !b && !c && !d;
                orderTimeLeft = endTime - nowTime;
            } else {
                orderTime = (!a || !b) && !d && !c;

                if (hourNow <= hourEnd) {
                    orderTimeLeft = endTime - nowTime;
                } else {
                    orderTimeLeft = MILLIS_IN_DAY - nowTime + endTime;
                }
            }
        }

        shopOpen = shop.isShopOpen();
        phoneNumber = shop.getPhoneNumber();
        terminalPayment = shop.isTerminalPayment();
        cashPayment = shop.isCashPayment();
        orderPreparationTime = shop.getOrderPreparationTime();

        if (shop.getStartPopUp() != null) {
            popupActive = shop.getStartPopUp().isActive();
            popupPath = shop.getStartPopUp().getPath();
            popupText = shop.getStartPopUp().getText();
        }
    }
}
