package com.mvp.kfz.repository;

import com.mvp.kfz.data.entity.CarFeature;
import com.mvp.kfz.data.entity.UserConfiguration;
import com.mvp.kfz.model.FeatureTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CarFeatureRepository extends JpaRepository<CarFeature, Long> {
    List<CarFeature> findAllByIdIn(List<Long> ids);

    CarFeature findFirstByFeatureTypeOrderByPriceAsc(FeatureTypes featureTypes);

    Optional<CarFeature> findByIdAndFeatureType(Long id, FeatureTypes featureType);
}
