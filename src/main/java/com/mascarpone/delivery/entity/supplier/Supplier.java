package com.mascarpone.delivery.entity.supplier;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "suppliers")
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "actual_address")
    private String actualAddress;

    @Column(name = "legal_address")
    private String legalAddress;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "ITN")
    private String itn;

    @Column(name = "IEC")
    private String iec;

    @Column(name = "PSRN")
    private String psrn;

    @Column(name = "shop_id")
    @JsonIgnore
    private long shopId;
}
