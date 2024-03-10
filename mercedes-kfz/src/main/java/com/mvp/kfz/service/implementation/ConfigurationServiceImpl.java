package com.mvp.kfz.service.implementation;

import com.mvp.kfz.data.entity.CarFeature;
import com.mvp.kfz.data.entity.UserConfiguration;
import com.mvp.kfz.data.entity.Users;
import com.mvp.kfz.model.FeatureTypes;
import com.mvp.kfz.repository.CarFeatureRepository;
import com.mvp.kfz.repository.UserConfigurationRepository;
import com.mvp.kfz.service.ConfigurationService;
import com.mvp.kfz.service.ContextUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class ConfigurationServiceImpl implements ConfigurationService {

    private final UserConfigurationRepository userConfigurationRepository;

    private final CarFeatureRepository carFeatureRepository;

    private final ContextUserService contextUserService;

    @Override
    public List<UserConfiguration> getConfigurations() {
        Users users = contextUserService.retrieveContextUser();
        return userConfigurationRepository.findByUserId(users.getId());
    }

    @Override
    public UserConfiguration addConfiguration(UserConfiguration newConfiguration) {
        newConfiguration.setUserId(contextUserService.retrieveContextUser().getId());
        validateCarFeatures(newConfiguration);
        return userConfigurationRepository.save(newConfiguration);
    }

    private void validateCarFeatures(UserConfiguration newConfiguration) {
        carFeatureRepository.findById(newConfiguration.getCarClass().getId()).ifPresentOrElse(
                extractCarFeatureConsumer(FeatureTypes.CLASS),
                () -> {throw new IllegalArgumentException();});
        carFeatureRepository.findById(newConfiguration.getCarType().getId()).ifPresentOrElse(
                extractCarFeatureConsumer(FeatureTypes.TYPE),
                () -> {throw new IllegalArgumentException();});
        carFeatureRepository.findById(newConfiguration.getCarMotor().getId()).ifPresentOrElse(
                extractCarFeatureConsumer(FeatureTypes.MOTOR),
                () -> {throw new IllegalArgumentException();});
        carFeatureRepository.findById(newConfiguration.getCarColor().getId()).ifPresentOrElse(
                extractCarFeatureConsumer(FeatureTypes.COLOR),
                () -> {throw new IllegalArgumentException();});
        List<CarFeature> extras = new ArrayList<>();
        for( CarFeature extra : newConfiguration.getCarExtras()) {
            carFeatureRepository.findById(extra.getId()).ifPresent(extras::add);
        }
        newConfiguration.setCarExtras(extras);
    }

    private static Consumer<CarFeature> extractCarFeatureConsumer(FeatureTypes featureTypes) {
        return carFeature -> {
            if (!carFeature.getFeatureType().equals(featureTypes)) {
                throw new IllegalArgumentException();
            }
        };
    }

    private void isUserCreatorOfProduct(UserConfiguration foundUserConfiguration) {
        Users users = contextUserService.retrieveContextUser();
        if(!users.getId().equals(foundUserConfiguration.getUserId())) {
            throw new IllegalCallerException("You are not the creator of the configuration " + foundUserConfiguration.getId());
        }
    }

    @Override
    public void deleteConfiguration(Long id) {
        UserConfiguration foundUserConfiguration = userConfigurationRepository.findById(id).orElseThrow();
        isUserCreatorOfProduct(foundUserConfiguration);
        userConfigurationRepository.deleteById(id);
    }

    @Override
    public UserConfiguration getConfigurationPrice(UserConfiguration newUserConfiguration) {
        if(Objects.isNull(newUserConfiguration.getCarClass()) || Objects.isNull(newUserConfiguration.getCarClass().getId()) || newUserConfiguration.getCarClass().getId() <= 0) {
            throw new IllegalArgumentException("Car class must be set");
        }
        return processUserConfigurationWithCalculatedPrice(newUserConfiguration);
    }

    private UserConfiguration processUserConfigurationWithCalculatedPrice(UserConfiguration newUserConfiguration) {
        newUserConfiguration.setPrice(0F);
        processCarConfigurationFeature(newUserConfiguration, newUserConfiguration.getCarClass());
        processCarConfigurationFeature(newUserConfiguration, newUserConfiguration.getCarType());
        processCarConfigurationFeature(newUserConfiguration, newUserConfiguration.getCarMotor());
        processCarConfigurationFeature(newUserConfiguration, newUserConfiguration.getCarColor());
        processCarExtras(newUserConfiguration);
        return newUserConfiguration;
    }

    private void processCarExtras(UserConfiguration newUserConfiguration) {
        List<CarFeature> foundExtras = new ArrayList<>();
        for(CarFeature extra : newUserConfiguration.getCarExtras()) {
            CarFeature foundFeature = carFeatureRepository.findByIdAndFeatureType(extra.getId(), FeatureTypes.EXTRAS)
                    .orElseThrow(() -> new IllegalArgumentException("No Type Found"));
            foundExtras.add(foundFeature);
            newUserConfiguration.setPrice(newUserConfiguration.getPrice() + foundFeature.getPrice());
        }
        newUserConfiguration.setCarExtras(foundExtras);
    }

    private void processCarConfigurationFeature(UserConfiguration newUserConfiguration, CarFeature carFeature) {
        if ((Objects.isNull(carFeature.getId()) || carFeature.getId() <= 0)) {
            carFeature = carFeatureRepository.findFirstByFeatureTypeOrderByPriceAsc(carFeature.getFeatureType());
        } else {
            carFeature = carFeatureRepository.findByIdAndFeatureType(carFeature.getId(),carFeature.getFeatureType())
                    .orElseThrow(() -> new IllegalArgumentException("No Type Found"));
        }
        newUserConfiguration.setPrice(newUserConfiguration.getPrice() + carFeature.getPrice());

        switch (carFeature.getFeatureType()) {
            case CLASS -> newUserConfiguration.setCarClass(carFeature);
            case TYPE -> newUserConfiguration.setCarType(carFeature);
            case MOTOR -> newUserConfiguration.setCarMotor(carFeature);
            case COLOR -> newUserConfiguration.setCarColor(carFeature);
            default -> throw new IllegalArgumentException("Invalid Feature Type" + carFeature.getFeatureType());
        }
    }
}
