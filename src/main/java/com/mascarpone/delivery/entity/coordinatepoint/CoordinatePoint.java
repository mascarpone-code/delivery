package com.mascarpone.delivery.entity.coordinatepoint;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mascarpone.delivery.entity.deliveryarea.DeliveryArea;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Table(name = "coordinates")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CoordinatePoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @ManyToOne
    @JoinColumn(name = "area_id")
    @JsonIgnore
    private DeliveryArea deliveryArea;
}
