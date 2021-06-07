package com.mascarpone.delivery.entity.productphoto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mascarpone.delivery.entity.product.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_photo")
public class ProductPhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product;

    @Lob
    @JsonIgnore
    private byte[] image;

    @Column(name = "mime_type")
    @JsonIgnore
    private String mimeType;

    @Column(name = "path")
    private String path;
}
