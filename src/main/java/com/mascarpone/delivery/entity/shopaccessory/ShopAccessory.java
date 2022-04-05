package com.mascarpone.delivery.entity.shopaccessory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "shop_accessory")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ShopAccessory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "shop_id")
    private Long shopId;

    @Column(name = "accessory_id")
    private Long accessoryId;
}
