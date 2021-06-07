package com.mascarpone.delivery.entity.shop;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mascarpone.delivery.entity.deliveryarea.DeliveryArea;
import com.mascarpone.delivery.entity.enums.PaymentBank;
import com.mascarpone.delivery.entity.nomenclature.Nomenclature;
import com.mascarpone.delivery.entity.payterminal.PayTerminal;
import com.mascarpone.delivery.entity.promocode.PromoCode;
import com.mascarpone.delivery.entity.startpopup.StartPopUp;
import com.mascarpone.delivery.entity.supplier.Supplier;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "shop")
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "prefix")
    private String prefix;

    @Column(name = "terminal")
    private String terminal;

    @Column(name = "password")
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "delivery")
    private boolean delivery;

    @Column(name = "pickup")
    private boolean pickup;

    @Column(name = "bonus_system")
    private boolean bonusSystem;

    @Column(name = "shop_open")
    private boolean shopOpen;

    @Column(name = "terminal_payment")
    private boolean terminalPayment;

    @Column(name = "cash_payment")
    private boolean cashPayment;

    @Column(name = "welcome_bonus_amount")
    private BigDecimal welcomeBonusAmount;

    @Column(name = "order_amount_percent")
    private BigDecimal orderAmountPercent;

    @Column(name = "bonus_pay_amount")
    private BigDecimal bonusPayAmount;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "shop", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<PromoCode> promoCodes = new ArrayList<>();

    @Column(name = "payment_bank")
    @Enumerated(value = EnumType.STRING)
    private PaymentBank paymentBank;

    @Column(name = "token_for_push", columnDefinition = "TEXT")
    private String tokenForPush;

    @Column(name = "push_token_kitchen", columnDefinition = "TEXT")
    private String tokenForPushKitchen;

    @Column(name = "push_token_courier", columnDefinition = "TEXT")
    private String tokenForPushCourier;

    @Column(name = "service_terms", columnDefinition = "TEXT")
    private String serviceTerms;

    @Column(name = "delivery_terms", columnDefinition = "TEXT")
    private String deliveryTerms;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "shop", cascade = CascadeType.ALL)
    private List<DeliveryArea> deliveryAreas;

    @Column(name = "order_begin_time")
    private String orderBeginTime;

    @Column(name = "order_end_time")
    private String orderEndTime;

    @Column(name = "time_zone")
    private String timeZone;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "shop_id")
    private List<Supplier> allSuppliers;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "shop_id")
    private List<Nomenclature> allNomenclatures;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "shop", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<PayTerminal> terminals;

    @Column(name = "order_preparation_time")
    private Integer orderPreparationTime;

    @Column(name = "flamp_link", columnDefinition = "TEXT")
    private String flampLink;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "shop_popup",
            joinColumns = {@JoinColumn(name = "shop_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "popup_id", referencedColumnName = "id")})
    private StartPopUp startPopUp;
}
