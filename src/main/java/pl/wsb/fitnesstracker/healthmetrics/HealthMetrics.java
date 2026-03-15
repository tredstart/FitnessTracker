package pl.wsb.fitnesstracker.healthmetrics;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class HealthMetrics {
    @Id
    private int id;
    private int user_id;
    private String date;
    private double weight;
    private double height;
    private double hearRate;
}



