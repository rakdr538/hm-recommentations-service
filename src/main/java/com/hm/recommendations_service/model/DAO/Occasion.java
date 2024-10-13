package com.hm.recommendations_service.model.DAO;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "occasion")
public class Occasion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "occasion_id")
    private Integer occasionId;

    @NotEmpty
    @NotNull
    @Column(name = "type")
    private String type;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private Promotion promotion;

    @Column(name = "occasion_start")
    private Timestamp occasionStart;

    @Column(name = "occasion_end")
    private Timestamp occasionEnd;

    @Column(name = "season")
    private String season;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", referencedColumnName = "country_id")
    private Country country;
}
