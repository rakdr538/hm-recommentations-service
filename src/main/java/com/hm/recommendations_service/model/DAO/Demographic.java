package com.hm.recommendations_service.model.DAO;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "demographics")
public class Demographic {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "demographic_id")
    private Integer demographicId;

    @Column(name = "gender")
    private String gender;

    @Column(name = "from_age")
    private Integer fromAge;

    @Column(name = "to_age")
    private Integer toAge;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private Promotion promotion;
}
