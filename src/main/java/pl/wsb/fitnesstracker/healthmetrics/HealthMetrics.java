package pl.wsb.fitnesstracker.healthmetrics;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name="HEALTH_METRICS")
@Entity
public class HealthMetrics {
    @Id
    private int id;
    private int user_id;
    private String date;
    private double weight;
    private double height;

    @Column(name = "heart_rate")
    private double heartRate;
}



