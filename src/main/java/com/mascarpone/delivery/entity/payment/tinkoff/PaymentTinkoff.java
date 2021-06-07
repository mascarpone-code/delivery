package com.mascarpone.delivery.entity.payment.tinkoff;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "payments_tinkoff")
@Getter
@Setter
@NoArgsConstructor
public class PaymentTinkoff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "id")
    private Long id;

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "amount")
    private String amount;

    @Column(name = "customer_key")
    private String customerKey;

    @Column(name = "error_code")
    private String errorCode;

    @Column(name = "terminal_key")
    private String terminalKey;

    @Column(name = "pan")
    private String pan;

    @Column(name = "rebill_id")
    private String rebillId;

    @Column(name = "payment_id")
    private String paymentId;

    @Column(name = "status_tinkoff")
    private String statusTinkoff;

    @Column(name = "status")
    private String status;

    @Column(name = "success")
    private String success;

    @Column(name = "token", length = 1000)
    private String token;

    @Column(name = "card_id")
    private String cardId;

    @Column(name = "card_type")
    private String cardType;

    @Column(name = "exp_date")
    private String expDate;

    @Column(name = "validation_token")
    private boolean validationToken;

    @Column(name = "payment_date")
    private Date paymentDate;
}
