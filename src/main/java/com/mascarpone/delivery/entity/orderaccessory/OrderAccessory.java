package com.mascarpone.delivery.entity.orderaccessory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mascarpone.delivery.entity.order.UserOrder;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_accessory")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class OrderAccessory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "accessory_id")
    private Long accessoryId;

    @Column(name = "name")
    private String name;

    @Column(name = "count")
    private Integer count;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private UserOrder order;
}
