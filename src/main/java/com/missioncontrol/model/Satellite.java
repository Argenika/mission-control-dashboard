package com.missioncontrol.model;

import jakarta.persistence.*;

@Entity
public class Satellite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String status;
    private double altitude;
    private double velocity;
    private double battery;
    private double temperature;

    public Satellite() {}

    public Satellite(String name, String status, double altitude, double velocity, double battery, double temperature) {
        this.name = name;
        this.status = status;
        this.altitude = altitude;
        this.velocity = velocity;
        this.battery = battery;
        this.temperature = temperature;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public double getAltitude() { return altitude; }
    public void setAltitude(double altitude) { this.altitude = altitude; }
    public double getVelocity() { return velocity; }
    public void setVelocity(double velocity) { this.velocity = velocity; }
    public double getBattery() { return battery; }
    public void setBattery(double battery) { this.battery = battery; }
    public double getTemperature() { return temperature; }
    public void setTemperature(double temperature) { this.temperature = temperature; }
}
