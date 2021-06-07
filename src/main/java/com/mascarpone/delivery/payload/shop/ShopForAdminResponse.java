package com.mascarpone.delivery.payload.shop;

import com.mascarpone.delivery.entity.shop.Shop;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ShopForAdminResponse {
    private Long id;
    private String name;
    private String prefix;
    private boolean isDelivery;
    private boolean isPickup;
    private boolean isBonusSystem;
    private BigDecimal welcomeBonusAmount;
    private BigDecimal orderAmountPercent;
    private BigDecimal bonusPayAmount;
    private String serviceTerms;
    private String deliveryTerms;
    private String orderBeginTime;
    private String orderEndTime;
    private String timeZone;
    private Boolean shopOpen;
    private String phoneNumber;
    private boolean terminalPayment;
    private boolean cashPayment;
    private Integer orderPreparationTime;
    private String flampLink;

    public ShopForAdminResponse(Shop shop) {
        id = shop.getId();
        name = shop.getName();
        prefix = shop.getPrefix();
        isDelivery = shop.isDelivery();
        isPickup = shop.isPickup();
        isBonusSystem = shop.isBonusSystem();
        welcomeBonusAmount = shop.getWelcomeBonusAmount();
        orderAmountPercent = shop.getOrderAmountPercent();
        bonusPayAmount = shop.getBonusPayAmount();
        serviceTerms = shop.getServiceTerms();
        deliveryTerms = shop.getDeliveryTerms();
        orderBeginTime = shop.getOrderBeginTime();
        orderEndTime = shop.getOrderEndTime();
        timeZone = shop.getTimeZone();
        shopOpen = shop.isShopOpen();
        phoneNumber = shop.getPhoneNumber();
        terminalPayment = shop.isTerminalPayment();
        cashPayment = shop.isCashPayment();
        orderPreparationTime = shop.getOrderPreparationTime();
        flampLink = shop.getFlampLink();
    }
}
