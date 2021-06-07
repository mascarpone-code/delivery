package com.mascarpone.delivery.entity.nomenclature;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mascarpone.delivery.entity.ingredient.Ingredient;
import com.mascarpone.delivery.entity.nomenclatureunit.NomenclatureUnit;
import com.mascarpone.delivery.entity.shopbranch.ShopBranch;
import com.mascarpone.delivery.entity.supplier.Supplier;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "nomenclature")
public class Nomenclature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "quantity")
    private Double quantity;

    @Column(name = "min_balance")
    private Double minBalance; //когда количество товара меньше этого значения, его будет необходимо заказать

    @Column(name = "quantity_for_order")
    private Double quantityForOrder; // количество товара на заказ

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private NomenclatureUnit nomenclatureUnit;

    @Column(name = "shop_id")
    @JsonIgnore
    private Long shopId;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "shop_branch_id")
    private ShopBranch shopBranch;

    @OneToMany(mappedBy = "nomenclature")
    private List<Ingredient> ingredients;
}
