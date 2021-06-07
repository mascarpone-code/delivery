package com.mascarpone.delivery.entity.deliveryarea;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mascarpone.delivery.entity.coordinatepoint.CoordinatePoint;
import com.mascarpone.delivery.entity.shop.Shop;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@Table(name = "delivery_area")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class DeliveryArea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "deliveryArea", cascade = CascadeType.ALL)
    private List<CoordinatePoint> coordinatePoints;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    @JsonIgnore
    private Shop shop;

    @Column(name = "min_order_amount")
    private BigDecimal minimumOrderAmount;

    @Column(name = "polygon_color")
    private String polygonColor;
}
