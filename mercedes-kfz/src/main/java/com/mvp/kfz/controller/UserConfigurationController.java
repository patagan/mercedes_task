package com.mvp.kfz.controller;

import com.mvp.kfz.data.entity.UserConfiguration;
import com.mvp.kfz.service.ConfigurationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserConfigurationController {

    private final ConfigurationService configurationService;

    @GetMapping(path = "/getConfigurations")
    public ResponseEntity<List> getConfigurations() {

        List<UserConfiguration> configurations = configurationService.getConfigurations();

        return ResponseEntity.ok(configurations);
    }
    @PostMapping("/addConfiguration")
    public ResponseEntity<UserConfiguration> addConfiguration(@RequestBody UserConfiguration newUserConfiguration) {

        UserConfiguration addedUserConfiguration = configurationService.addConfiguration(newUserConfiguration);
        return ResponseEntity.ok(addedUserConfiguration);
    }


    @DeleteMapping(path = "/deleteConfiguration")
    public ResponseEntity<Long> deleteConfiguration(@RequestBody Long id) {

        configurationService.deleteConfiguration(id);
        return ResponseEntity.ok(id);
    }

    @PostMapping("/getConfigurationPrice")
    public ResponseEntity<UserConfiguration> getConfigurationPrice(@RequestBody UserConfiguration newUserConfiguration) {
        UserConfiguration uerConfigurationWithCalculatedPrice = configurationService.getConfigurationPrice(newUserConfiguration);
        return ResponseEntity.ok(uerConfigurationWithCalculatedPrice);
    }
}
