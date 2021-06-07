package com.mascarpone.delivery.entity.startpopup;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mascarpone.delivery.entity.shop.Shop;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Table(name = "popup")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class StartPopUp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Lob
    @JsonIgnore
    @Column(name = "image")
    private byte[] image;

    @Column(name = "mime_type")
    @JsonIgnore
    private String mimeType;

    @Column(name = "path")
    private String path;

    @Column(columnDefinition = "TEXT")
    private String text;

    @OneToOne(mappedBy = "startPopUp")
    @JsonIgnore
    private Shop shop;

    @Column(name = "active")
    private boolean active;
}
