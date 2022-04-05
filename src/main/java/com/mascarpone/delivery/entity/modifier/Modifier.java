package com.mascarpone.delivery.entity.modifier;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mascarpone.delivery.entity.product.Product;
import com.mascarpone.delivery.entity.shop.Shop;
import com.mascarpone.delivery.entity.unit.Unit;
import com.mascarpone.delivery.entity.user.User;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "modifier")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Modifier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "products_modifiers",
            joinColumns = @JoinColumn(name = "modifier_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    @JsonProperty(value = "products")
    @JsonIgnore
    private Set<Product> products;

    @Column(name = "max_Count")
    private Double maxCount;
}
