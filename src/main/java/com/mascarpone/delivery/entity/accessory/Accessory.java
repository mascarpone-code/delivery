package com.mascarpone.delivery.entity.accessory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Table(name = "accessory")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Accessory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;
}
