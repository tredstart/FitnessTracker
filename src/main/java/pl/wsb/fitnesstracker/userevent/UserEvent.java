package pl.wsb.fitnesstracker.userevent;

import jakarta.persistence.*;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.event.Event;

@Entity
@Table(name = "user_event")
public class UserEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    private String status;
}