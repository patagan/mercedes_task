package com.mvp.kfz.controller;

import com.mvp.kfz.data.entity.UserConfiguration;
import com.mvp.kfz.service.ConfigurationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserConfigurationController {

    private final ConfigurationService configurationService;


    @Operation(summary = "Get configurations", description = "Get all user configurations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found user configurations",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserConfiguration.class))})
    })
    @GetMapping(path = "/getConfigurations")
    public ResponseEntity<List<UserConfiguration>> getConfigurations() {

        List<UserConfiguration> configurations = configurationService.getConfigurations();

        return ResponseEntity.ok(configurations);
    }

    @Operation(summary = "Add or update user configuration", description = "Add or update a user configuration")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully added or updated user configuration",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserConfiguration.class))})
    })
    @PostMapping("/addConfiguration")
    public ResponseEntity<UserConfiguration> addConfiguration(@RequestBody UserConfiguration newUserConfiguration) {

        UserConfiguration addedUserConfiguration = configurationService.addConfiguration(newUserConfiguration);
        return ResponseEntity.ok(addedUserConfiguration);
    }

    @Operation(summary = "Delete configuration", description = "Delete a user configuration by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ID of the deleted user configuration",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Long.class))})
    })
    @DeleteMapping(path = "/deleteConfiguration")
    public ResponseEntity<Long> deleteConfiguration(@RequestBody Long id) {

        configurationService.deleteConfiguration(id);
        return ResponseEntity.ok(id);
    }

    @Operation(summary = "Get configuration price", description = "Calculate the price of a user configuration")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User configuration with calculated price or adapted basic configuration with price",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserConfiguration.class))})
    })
    @PostMapping("/getConfigurationPrice")
    public ResponseEntity<UserConfiguration> getConfigurationPrice(@RequestBody UserConfiguration newUserConfiguration) {
        UserConfiguration uerConfigurationWithCalculatedPrice = configurationService.getConfigurationPrice(newUserConfiguration);
        return ResponseEntity.ok(uerConfigurationWithCalculatedPrice);
    }
}
