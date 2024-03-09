package com.mvp.kfz.service.implementation;

import com.mvp.kfz.data.entity.CarFeature;
import com.mvp.kfz.model.FeatureCategories;
import com.mvp.kfz.model.FeatureTypes;
import com.mvp.kfz.repository.CarFeatureRepository;
import com.mvp.kfz.service.CarFeatureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarFeatureServiceImpl implements CarFeatureService {

    private final CarFeatureRepository carFeatureRepository;
    @Override
    public FeatureCategories getCarFeatures() {
        List<CarFeature> carFeatureList = carFeatureRepository.findAll();
        return FeatureCategories.builder()
                .carExtras(extractFeatureTypes(carFeatureList, FeatureTypes.EXTRAS))
                .carTypes(extractFeatureTypes(carFeatureList, FeatureTypes.TYPE))
                .carColors(extractFeatureTypes(carFeatureList, FeatureTypes.COLOR))
                .carClasses(extractFeatureTypes(carFeatureList, FeatureTypes.CLASS))
                .carMotors(extractFeatureTypes(carFeatureList, FeatureTypes.MOTOR))
                .build();
    }

    private static List<CarFeature> extractFeatureTypes(List<CarFeature> carFeatureList, FeatureTypes feature) {
        return carFeatureList.stream().filter(carFeature -> carFeature.getFeatureType().equals(feature)).toList();
    }
}
