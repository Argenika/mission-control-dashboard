package com.missioncontrol.controller;

import com.missioncontrol.model.Alert;
import com.missioncontrol.model.Satellite;
import com.missioncontrol.service.MissionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class MissionController {

    private final MissionService missionService;

    public MissionController(MissionService missionService) {
        this.missionService = missionService;
    }

    @GetMapping("/satellites")
    public List<Satellite> getSatellites() {
        return missionService.getAllSatellites();
    }

    @GetMapping("/alerts")
    public List<Alert> getAlerts() {
        return missionService.getAllAlerts();
    }

    @GetMapping("/alerts/{name}")
    public List<Alert> getAlertsBySatellite(@PathVariable String name) {
        return missionService.getAlertsBySatellite(name);
    }
}
