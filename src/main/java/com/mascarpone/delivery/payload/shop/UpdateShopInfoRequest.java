package com.mascarpone.delivery.payload.shop;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@Getter
@AllArgsConstructor
public class UpdateShopInfoRequest {
    private Optional<Boolean> delivery; // is delivery available
    private Optional<Boolean> pickup; // is pick-up available
    private Optional<Boolean> bonusSystem; // is bonus system available
    private Optional<Boolean> shopOpen; // is shop open
    private Optional<Boolean> terminalPayment; // is payment via terminal available
    private Optional<Boolean> cashPayment; // is cash payment available
    private Optional<BigDecimal> welcomeBonusAmount; // amount of welcome bonuses
    private Optional<BigDecimal> orderAmountPercent; // amount of credited bonuses as a percentage of the order price
    private Optional<BigDecimal> bonusPayAmount; // maximum bonus payment amount as a percentage of the order price
    private Optional<String> serviceTerms;
    private Optional<String> deliveryTerms;
    private Optional<String> orderBeginTime; // start time for accepting orders in HH:mm format
    private Optional<String> orderEndTime; // order acceptance end time in HH:mm format
    private Optional<String> timeZone; // time zone of the shop
    private Optional<String> phoneNumber; // phone number in 79xxxxxxxxx format
    private Optional<String> flampLink;
    private Optional<Integer> orderPreparationTime; // time after which the order will be delivered/issued with the mark "for the nearest future"
}
