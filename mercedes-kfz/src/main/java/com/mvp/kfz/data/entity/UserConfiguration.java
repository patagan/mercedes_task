package com.mvp.kfz.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserConfiguration {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "class_id", referencedColumnName = "id")
    private CarFeature carClass;

    @ManyToOne
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    private CarFeature carType;

    @ManyToOne
    @JoinColumn(name = "motor_id", referencedColumnName = "id")
    private CarFeature carMotor;

    @ManyToOne
    @JoinColumn(name = "color_id", referencedColumnName = "id")
    private CarFeature carColor;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_configuration_car_feature",
            joinColumns = @JoinColumn(name = "user_configuration_id"),
            inverseJoinColumns = @JoinColumn(name = "car_feature_id")
    )
    private List<CarFeature> carExtras;

    @Column(nullable = false)
    private Float price;
    @Column(nullable = false)
    private Long userId;
}
