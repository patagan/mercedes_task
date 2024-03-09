package com.mvp.kfz.repository;

import com.mvp.kfz.data.entity.UserConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserConfigurationRepository extends JpaRepository<UserConfiguration, Long> {

    List<UserConfiguration> findByUserId(Long userId);
}