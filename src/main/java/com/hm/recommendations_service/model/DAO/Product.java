package com.hm.recommendations_service.model.DAO;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer productId;

    @NotNull
    @Column(name = "category_id")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "categoryId", orphanRemoval = true)
    private List<Category> categories = new ArrayList<>();

    @Column(name = "occasion_id")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "occasionId", orphanRemoval = true)
    private List<Occasion> occasions = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private Promotion promotion;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "variation_id", referencedColumnName = "variation_id")
    private Variation variation;

    @NotNull
    @Column(name = "discounted_price")
    private Float discountedPrice;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "in_stock")
    private Boolean inStock;
}

