package com.mascarpone.delivery.entity.unit;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Table(name = "unit")
public class Unit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;
}
