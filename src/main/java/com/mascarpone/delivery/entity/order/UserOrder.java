package com.mascarpone.delivery.entity.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mascarpone.delivery.entity.enums.OrderStatus;
import com.mascarpone.delivery.entity.enums.OrderType;
import com.mascarpone.delivery.entity.enums.PayType;
import com.mascarpone.delivery.entity.enums.UserOrderType;
import com.mascarpone.delivery.entity.orderaccessory.OrderAccessory;
import com.mascarpone.delivery.entity.orderproduct.OrderProduct;
import com.mascarpone.delivery.entity.shop.Shop;
import com.mascarpone.delivery.entity.user.User;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "orders")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date_create")
    private Date dateCreate;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    @JsonIgnore
    private Shop shop;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User creator;

    @ManyToOne
    @JoinColumn(name = "courier_id")
    @JsonIgnore
    private User courier;

    @ManyToOne
    @JoinColumn(name = "cook_id")
    @JsonIgnore
    private User cook;

    @Column(name = "uuid")
    private String UUID;

    @Column(name = "uuid_ios")
    private Long UUIDIos;

    @Column(name = "order_number")
    private Long orderNumber;

    @Column(name = "address", columnDefinition = "TEXT")
    private String address;

    @Column(name = "full_price")
    private BigDecimal fullPrice;

    @Column(name = "paid_by_bonus")
    private BigDecimal paidByBonus;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "proteins")
    private Double proteins;

    @Column(name = "fats")
    private Double fats;

    @Column(name = "carbohydrates")
    private Double carbohydrates;

    @Column(name = "kilo_calories")
    private Double kiloCalories;

    @Column(name = "weight")
    private Double weight;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProducts;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private OrderStatus status;

    @Column(name = "type")
    @Enumerated(value = EnumType.STRING)
    private OrderType type;

    @Column(name = "user_note", columnDefinition = "TEXT")
    private String userNote;

    @Column(name = "admin_note", columnDefinition = "TEXT")
    @JsonIgnore
    private String adminNote;

    @Column(name = "is_paid")
    private boolean paid;

    @Column(name = "branch_id")
    private Long shopBranchId;

    @Column(name = "delivery_area_id")
    private Long deliveryAreaId;

    @Column(name = "order_type")
    @Enumerated(value = EnumType.STRING)
    private UserOrderType userOrderType;

    @Column(name = "pay_type")
    @Enumerated(value = EnumType.STRING)
    private PayType payType;

    @Column(name = "order_time")
    private String orderTime;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderAccessory> orderAccessories;
}
