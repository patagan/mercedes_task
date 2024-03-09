package com.mvp.kfz.model;

import com.mvp.kfz.data.entity.CarFeature;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class FeatureCategories {

    private List<CarFeature> carMotors;
    private List<CarFeature> carColors;
    private List<CarFeature> carClasses;
    private List<CarFeature> carTypes;
    private List<CarFeature> carExtras;
}
