package com.hm.recommendations_service.model.DAO;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "variations")
public class Variation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "variation_id")
    private Integer variationId;

    @NotNull
    @Column(name = "product_size")
    private String productSize;

    @NotNull
    @Column(name = "product_code")
    private String productCode;

    @Column(name = "product_color")
    private String productColor;

    @Column(name = "product_images")
    private List<String> productImages;

    @Column(name = "product_thumbnails")
    private List<String> productThumbnails;

    @NotNull
    @Column(name = "sku")
    private Long sku;

    @NotNull
    @Column(name = "brand")
    private String brand;

    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "original_price")
    private Float originalPrice;
}
