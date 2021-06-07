package com.mascarpone.delivery.entity.bonustransaction;

import com.mascarpone.delivery.entity.enums.BonusTransactionType;
import com.mascarpone.delivery.entity.order.UserOrder;
import com.mascarpone.delivery.entity.shop.Shop;
import com.mascarpone.delivery.entity.user.User;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@Table(name = "bonus_transactions")
public class BonusTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User customer;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private UserOrder order;

    @Column(name = "date_create")
    private Date dateCreate;

    @Column(name = "type")
    @Enumerated(value = EnumType.STRING)
    private BonusTransactionType type;

    @Column(name = "bonus_amount")
    private BigDecimal bonusAmount;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "customer_phone_number")
    private String customerPhoneNumber;
}
