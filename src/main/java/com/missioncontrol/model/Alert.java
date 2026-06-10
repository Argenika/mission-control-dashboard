package com.missioncontrol.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String satelliteName;
    private String type;
    private String message;
    private String severity;
    private LocalDateTime timestamp;

    public Alert() {}

    public Alert(String satelliteName, String type, String message, String severity) {
        this.satelliteName = satelliteName;
        this.type = type;
        this.message = message;
        this.severity = severity;
        this.timestamp = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public String getSatelliteName() { return satelliteName; }
    public String getType() { return type; }
    public String getMessage() { return message; }
    public String getSeverity() { return severity; }
    public LocalDateTime getTimestamp() { return timestamp; }
}
