package com.mvp.kfz.controller;

import com.mvp.kfz.model.FeatureCategories;
import com.mvp.kfz.service.CarFeatureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ConfigurableFeaturesController {

    private final CarFeatureService carFeatureService;

    @Operation(summary = "Get all car features", description = "Get available car features grouped by featureType")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Car Features grouped by featureType",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FeatureCategories.class))})
    })
    @GetMapping(path = "/getCarFeatures")
    public ResponseEntity<FeatureCategories> getCarFeatures() {

        FeatureCategories configurations = carFeatureService.getCarFeatures();
        return ResponseEntity.ok(configurations);
    }
}
