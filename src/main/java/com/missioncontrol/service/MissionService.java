package com.missioncontrol.service;

import com.missioncontrol.model.Alert;
import com.missioncontrol.model.Satellite;
import com.missioncontrol.repository.AlertRepository;
import com.missioncontrol.repository.SatelliteRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class MissionService {

    private final SatelliteRepository satelliteRepo;
    private final AlertRepository alertRepo;
    private final Random random = new Random();

    public MissionService(SatelliteRepository satelliteRepo, AlertRepository alertRepo) {
        this.satelliteRepo = satelliteRepo;
        this.alertRepo = alertRepo;
        initSatellites();
    }

    private void initSatellites() {
        if (satelliteRepo.count() == 0) {
            satelliteRepo.save(new Satellite("ISS", "NOMINAL", 408.0, 27600.0, 95.0, 21.5));
            satelliteRepo.save(new Satellite("Sentinel-1A", "NOMINAL", 693.0, 7500.0, 88.0, -10.0));
            satelliteRepo.save(new Satellite("Galileo-FOC1", "NOMINAL", 23222.0, 3800.0, 72.0, -40.0));
        }
    }

    @Scheduled(fixedRate = 5000)
    public void updateTelemetry() {
        List<Satellite> satellites = satelliteRepo.findAll();
        for (Satellite sat : satellites) {
            sat.setVelocity(sat.getVelocity() + (random.nextDouble() - 0.5) * 10);
            sat.setBattery(Math.max(0, Math.min(100, sat.getBattery() + (random.nextDouble() - 0.5) * 2)));
            sat.setTemperature(sat.getTemperature() + (random.nextDouble() - 0.5) * 1.5);
            sat.setAltitude(sat.getAltitude() + (random.nextDouble() - 0.5) * 0.5);

            if (sat.getBattery() < 20) {
                alertRepo.save(new Alert(sat.getName(), "BATTERY", "Battery critical: " + String.format("%.1f", sat.getBattery()) + "%", "HIGH"));
                sat.setStatus("WARNING");
            } else if (sat.getTemperature() > 50 || sat.getTemperature() < -60) {
                alertRepo.save(new Alert(sat.getName(), "TEMPERATURE", "Temperature out of range: " + String.format("%.1f", sat.getTemperature()) + "°C", "HIGH"));
                sat.setStatus("WARNING");
            } else {
                sat.setStatus("NOMINAL");
            }
            satelliteRepo.save(sat);
        }
    }

    public List<Satellite> getAllSatellites() { return satelliteRepo.findAll(); }
    public List<Alert> getAllAlerts() { return alertRepo.findAll(); }
    public List<Alert> getAlertsBySatellite(String name) { return alertRepo.findBySatelliteName(name); }
}
