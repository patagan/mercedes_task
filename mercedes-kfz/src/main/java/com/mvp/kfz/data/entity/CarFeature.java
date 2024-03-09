package com.mvp.kfz.data.entity;

import com.mvp.kfz.model.FeatureTypes;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarFeature {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private FeatureTypes featureType;
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Float price;
}
