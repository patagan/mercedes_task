package com.mvp.kfz.service;

import com.mvp.kfz.data.entity.UserConfiguration;

import java.util.List;

public interface ConfigurationService {

    List<UserConfiguration> getConfigurations();
    UserConfiguration addConfiguration(UserConfiguration newConfiguration);

    void deleteConfiguration(Long id);

    UserConfiguration getConfigurationPrice(UserConfiguration newUserConfiguration);
}
