package pl.wsb.fitnesstracker.user.api;

import jakarta.persistence.*;
import lombok.*;
import pl.wsb.fitnesstracker.statistics.api.Statistics;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = "statistics")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "birthdate", nullable = false)
    private LocalDate birthdate;

    @Column(nullable = false, unique = true)
    private String email;

    @Setter
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "statistics_id", referencedColumnName = "id")
    private Statistics statistics;

    public User(
            final String firstName,
            final String lastName,
            final LocalDate birthdate,
            final String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.email = email;
    }
}