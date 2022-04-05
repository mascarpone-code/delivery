package com.mascarpone.delivery.entity.ordermodifier;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mascarpone.delivery.entity.orderproduct.OrderProduct;
import com.mascarpone.delivery.entity.shop.Shop;
import com.mascarpone.delivery.entity.unit.Unit;
import com.mascarpone.delivery.entity.user.User;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "modifier_order")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class OrderModifier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "modifier_id")
    private Long modifierId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
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

    @Column(name = "date_create")
    private Date dateCreate;

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private Unit unit;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User creator;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    @JsonIgnore
    private Shop shop;

    @Column(name = "max_Count")
    private Double maxCount;

    @Column(name = "count")
    private Integer count;

    @ManyToOne
    @JoinColumn(name = "order_product_id")
    @JsonIgnore
    private OrderProduct orderProduct;
}
