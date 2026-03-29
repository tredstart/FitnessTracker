package pl.wsb.fitnesstracker.healthmetrics;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.wsb.fitnesstracker.user.api.User;

import java.time.LocalDate;

@Entity
@Table(name = "health_metrics")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HealthMetrics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private double weight;

    @Column(nullable = false)
    private double height;

    @Column(name = "heart_rate")
    private double heartRate;

    public HealthMetrics(User user, LocalDate date, double weight, double height, double heartRate) {
        this.user = user;
        this.date = date;
        this.weight = weight;
        this.height = height;
        this.heartRate = heartRate;
    }

    public HealthMetrics(Long id, User user, LocalDate date, double weight, double height, double heartRate) {
        this.id = id;
        this.user = user;
        this.date = date;
        this.weight = weight;
        this.height = height;
        this.heartRate = heartRate;
    }
}