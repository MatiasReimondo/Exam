package com.examM.solarSystem.Repository;

import com.examM.solarSystem.Model.Prediction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PredictionRepository extends JpaRepository<Prediction,Long> {
}
