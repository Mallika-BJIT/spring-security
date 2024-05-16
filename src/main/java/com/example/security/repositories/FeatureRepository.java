package com.example.security.repositories;

import com.example.security.model.Feature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FeatureRepository extends JpaRepository<Feature, Long> {
    List<Feature> findByFeatureNameIn(List<String> featureNames);

    Optional<Feature> findByFeatureName(String featureName);
}
