package com.mascarpone.delivery.entity.nomenclatureunit;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Table(name = "nomenclature_unit")
public class NomenclatureUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;
}
