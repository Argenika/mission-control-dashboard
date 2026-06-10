package com.missioncontrol.repository;

import com.missioncontrol.model.Satellite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SatelliteRepository extends JpaRepository<Satellite, Long> {
}
