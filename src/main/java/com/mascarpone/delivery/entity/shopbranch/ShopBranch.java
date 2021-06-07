package com.mascarpone.delivery.entity.shopbranch;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mascarpone.delivery.entity.shop.Shop;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "shop_branch")
public class ShopBranch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    @JsonIgnore
    private Shop shop;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "address", columnDefinition = "TEXT")
    private String address;

    @Column(name = "active")
    private boolean active;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "ITN")
    private Long itn;

    @Column(name = "IEC")
    private Long iec;

    @Column(name = "PSRN")
    private Long psrn;
}
