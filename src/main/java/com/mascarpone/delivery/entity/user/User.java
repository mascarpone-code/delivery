package com.mascarpone.delivery.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mascarpone.delivery.entity.deliveryaddress.DeliveryAddress;
import com.mascarpone.delivery.entity.enums.AccountType;
import com.mascarpone.delivery.entity.pushtoken.PushToken;
import com.mascarpone.delivery.entity.shop.Shop;
import com.mascarpone.delivery.entity.shopbranch.ShopBranch;
import com.mascarpone.delivery.entity.userbonusaccount.UserBonusAccount;
import com.mascarpone.delivery.entity.userrole.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(name = "shop", columnNames = "shop_id"),
                @UniqueConstraint(name = "shop_branch", columnNames = "shop_branch_id")
        }
)
public class User {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID uuid;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    @JsonIgnore
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "address", columnDefinition = "TEXT")
    private String address;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    private List<DeliveryAddress> addresses = new ArrayList<>();

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "sms_code")
    @JsonIgnore
    private String smsCode;

    @Column(name = "sms_code_send_date")
    @JsonIgnore
    private Date smsCodeSendDate;

    @Column(name = "sms_code_enter_count")
    @JsonIgnore
    private int smsCodeEnterCount;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<UserRole> roles = new HashSet<>();

    @Column(name = "date_create")
    @JsonIgnore
    private Date dateCreate;

    @Column(name = "password_reset_code")
    @JsonIgnore
    private String passwordResetCode;

    @Column(name = "date_reset_password")
    @JsonIgnore
    private Date dateResetPassword;

    @Column(name = "password_reset")
    @JsonIgnore
    private Boolean passwordReset;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    @JsonIgnore
    private Shop shop;

    @Column(name = "shop_prefix")
    private String shopPrefix;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bonus_account_id")
    @JsonIgnore
    private UserBonusAccount bonusAccount;

    @Column(name = "passport", columnDefinition = "TEXT")
    private String passport;

    @Column(name = "phone_number")
    private String phoneNumber;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<PushToken> pushTokens = new ArrayList<>();

    @Column(name = "courier_active")
    private Boolean courierActive;

    @Column(name = "type")
    @Enumerated(value = EnumType.STRING)
    private AccountType accountType;

    @ManyToOne
    @JoinColumn(name = "shop_branch_id")
    @JsonIgnore
    private ShopBranch shopBranch;
}
