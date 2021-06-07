package com.mascarpone.delivery.entity.userbonusaccount;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mascarpone.delivery.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_bonus_account")
public class UserBonusAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(mappedBy = "bonusAccount")
    @JsonIgnore
    private User customer;

    @Column(name = "bonus_amount")
    private BigDecimal bonusAmount;
}
