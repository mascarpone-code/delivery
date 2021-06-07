package com.mascarpone.delivery.entity.promocode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mascarpone.delivery.entity.shop.Shop;
import com.mascarpone.delivery.entity.user.User;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "promo_code")
public class PromoCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "promo_code")
    private String promoCode;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "date_create")
    private Date dateCreate;

    @Column(name = "valid_from")
    private Date validFrom;

    @Column(name = "valid_to")
    private Date validTo;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    @JsonIgnore
    private User creator;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    @JsonIgnore
    private Shop shop;

    public PromoCode(Long id, String promoCode, BigDecimal price, Date dateCreate, Date validFrom, Date validTo) {
        this.id = id;
        this.promoCode = promoCode;
        this.price = price;
        this.dateCreate = dateCreate;
        this.validFrom = validFrom;
        this.validTo = validTo;
    }
}
