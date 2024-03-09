package com.mvp.kfz.controller;

import com.mvp.kfz.data.entity.CarFeature;
import com.mvp.kfz.data.entity.UserConfiguration;
import com.mvp.kfz.model.FeatureCategories;
import com.mvp.kfz.service.CarFeatureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ConfigurableFeaturesController {

    private final CarFeatureService carFeatureService;
    @GetMapping(path = "/getCarFeatures")
    public ResponseEntity<FeatureCategories> getCarFeatures() {

        FeatureCategories configurations = carFeatureService.getCarFeatures();
        return ResponseEntity.ok(configurations);
    }
}
