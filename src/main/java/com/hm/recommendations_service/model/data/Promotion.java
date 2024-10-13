package com.hm.recommendations_service.model.data;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table (name = "promotions")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Promotion {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "promotion_id")
    private Integer id;

    private String name;

    private String description;

    @Column(name = "start_timestamp")
    private Timestamp startTime;

    @Column(name = "end_timestamp")
    private Timestamp endTime;

    private Float discount;
}
