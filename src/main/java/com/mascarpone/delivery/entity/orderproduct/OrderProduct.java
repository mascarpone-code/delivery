package com.mascarpone.delivery.entity.orderproduct;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mascarpone.delivery.entity.order.UserOrder;
import com.mascarpone.delivery.entity.ordermodifier.OrderModifier;
import com.mascarpone.delivery.entity.productphoto.ProductPhoto;
import com.mascarpone.delivery.entity.shop.Shop;
import com.mascarpone.delivery.entity.unit.Unit;
import com.mascarpone.delivery.entity.user.User;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "product_order")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "name")
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

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

    @Column(name = "group_id")
    private Long productGroupId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User creator;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    @JsonIgnore
    private Shop shop;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = CascadeType.ALL)
    private Set<ProductPhoto> photos = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private Unit unit;

    @Column(name = "count")
    private Integer count;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "orderProduct", cascade = CascadeType.ALL)
    private List<OrderModifier> orderModifiers;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private UserOrder order;
}
