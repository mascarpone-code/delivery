package com.mascarpone.delivery.entity.prescriptioncard;

import com.mascarpone.delivery.entity.ingredient.Ingredient;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "prescription_card")
public class PrescriptionCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToMany(mappedBy = "prescriptionCard")
    private List<Ingredient> ingredients;
}
