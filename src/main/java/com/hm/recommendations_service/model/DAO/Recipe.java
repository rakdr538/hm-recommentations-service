package com.hm.recommendations_service.model.DAO;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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
@Table(name = "recipes")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id")
    private Integer recipeId;

    @NotNull
    @NotEmpty
    @Column(name = "name")
    private String name;

    @Column(name = "product_id")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "productId")
    private List<Product> products = new ArrayList<>();

    @NotNull
    @Column(name = "recipe_original_price")
    private Float recipeOriginalPrice;

    @NotNull
    @Column(name = "recipe_discounted_price")
    private Float recipeDiscountedPrice;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private Promotion promotion;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private Occasion occasion;
}
