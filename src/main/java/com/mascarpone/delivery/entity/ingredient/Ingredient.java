package com.mascarpone.delivery.entity.ingredient;

import com.mascarpone.delivery.entity.nomenclature.Nomenclature;
import com.mascarpone.delivery.entity.prescriptioncard.PrescriptionCard;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Table(name = "ingredients")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private PrescriptionCard prescriptionCard;

    @ManyToOne
    @JoinColumn(name = "nomenclature_id")
    private Nomenclature nomenclature;

    @Column(name = "nomenclature_quantity")
    private double nomenclatureQuantity;
}
