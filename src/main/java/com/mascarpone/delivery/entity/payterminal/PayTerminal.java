package com.mascarpone.delivery.entity.payterminal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mascarpone.delivery.entity.enums.PayTerminalType;
import com.mascarpone.delivery.entity.shop.Shop;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pay_terminal")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PayTerminal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "terminal")
    private String terminal;

    @Column(name = "password")
    private String password;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    @JsonIgnore
    private Shop shop;

    @Column(name = "payment_type")
    @Enumerated(value = EnumType.STRING)
    private PayTerminalType terminalType;
}
