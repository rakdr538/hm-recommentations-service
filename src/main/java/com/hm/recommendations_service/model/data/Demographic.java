package com.hm.recommendations_service.model.data;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="demographics")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Demographic {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "demographic_id")
    private Integer id;

    @Column(name = "gender")
    private String gender;

    @Column(name = "from_age")
    private Integer fromAge;

    @Column(name = "to_age")
    private Integer toAge;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "promotion_id", referencedColumnName = "promotion_id")
    private Promotion promotion;

}
