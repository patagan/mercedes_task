package com.mvp.kfz.service.implementation;

import com.mvp.kfz.data.entity.CarFeature;
import com.mvp.kfz.model.FeatureCategories;
import com.mvp.kfz.model.FeatureTypes;
import com.mvp.kfz.repository.CarFeatureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class CarFeatureServiceImplTest {

    @Mock
    private CarFeatureRepository carFeatureRepository;

    @InjectMocks
    private CarFeatureServiceImpl carFeatureService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void WhenCarFeaturesThenGroupThemAndReturnFeatureCategories() {
        // Given
        List<CarFeature> carFeatureList = new ArrayList<>();
        carFeatureList.add(CarFeature.builder().id(1L).name("Extra 1").featureType(FeatureTypes.EXTRAS).build());
        carFeatureList.add(CarFeature.builder().id(2L).name("Extra 2").featureType(FeatureTypes.EXTRAS).build());
        carFeatureList.add(CarFeature.builder().id(3L).name("Type 1").featureType(FeatureTypes.TYPE).build());
        carFeatureList.add(CarFeature.builder().id(4L).name("Type 2").featureType(FeatureTypes.TYPE).build());
        carFeatureList.add(CarFeature.builder().id(5L).name("Color 1").featureType(FeatureTypes.COLOR).build());
        carFeatureList.add(CarFeature.builder().id(6L).name("Class 1").featureType(FeatureTypes.CLASS).build());
        carFeatureList.add(CarFeature.builder().id(7L).name("Motor 1").featureType(FeatureTypes.MOTOR).build());

        when(carFeatureRepository.findAll()).thenReturn(carFeatureList);

        // When
        FeatureCategories result = carFeatureService.getCarFeatures();

        // Then
        assertNotNull(result);
        assertEquals(2, result.getCarExtras().size());
        assertEquals(2, result.getCarTypes().size());
        assertEquals(1, result.getCarColors().size());
        assertEquals(1, result.getCarClasses().size());
        assertEquals(1, result.getCarMotors().size());
    }

}