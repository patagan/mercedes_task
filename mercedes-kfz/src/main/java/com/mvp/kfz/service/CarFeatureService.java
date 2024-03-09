package com.mvp.kfz.service;

import com.mvp.kfz.data.entity.CarFeature;
import com.mvp.kfz.model.FeatureCategories;

import java.util.List;

public interface CarFeatureService {
    FeatureCategories getCarFeatures();
}
