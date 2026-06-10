package com.missioncontrol.repository;

import com.missioncontrol.model.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AlertRepository extends JpaRepository<Alert, Long> {
    List<Alert> findBySatelliteName(String satelliteName);
}
