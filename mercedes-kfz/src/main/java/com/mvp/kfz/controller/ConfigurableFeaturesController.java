package com.mvp.kfz.controller;

import com.mvp.kfz.model.FeatureCategories;
import com.mvp.kfz.service.CarFeatureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ConfigurableFeaturesController {

    private final CarFeatureService carFeatureService;
    @GetMapping(path = "/getCarFeatures")
    public ResponseEntity<FeatureCategories> getCarFeatures() {

        FeatureCategories configurations = carFeatureService.getCarFeatures();
        return ResponseEntity.ok(configurations);
    }
}
