package pl.wsb.fitnesstracker;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.wsb.fitnesstracker.event.Event;
import pl.wsb.fitnesstracker.event.EventParticipantCount;
import pl.wsb.fitnesstracker.event.EventRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * LAB03 — STAGE 1: encje.
 *
 * Ten plik sprawdza tylko istnienie i strukturę tabel dla nowych encji LAB03
 * (Event, UserEvent, WorkoutSession). NIE używa żadnego repozytorium — zieleni
 * się wyłącznie na podstawie poprawnych encji JPA.
 *
 * Class should be under src/test/java/pl/wsb/fitnesstracker.
 *
 * Wymagane nazwy tabel (np. przez @Table(name = "...")):
 *   - event
 *   - user_event (z kolumnami user_id, event_id)
 *   - workout_session (z kolumną training_id)
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class Lab03EntitiesTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private EventRepository eventRepository;

    @Test
    void shouldHaveEventTable() throws Exception {
        try (Connection conn = dataSource.getConnection()) {
            assertThat(tableExists(conn, "event")).isTrue();
        }
    }

    @Test
    void shouldHaveUserEventTable() throws Exception {
        try (Connection conn = dataSource.getConnection()) {
            assertThat(tableExists(conn, "user_event")).isTrue();
        }
    }

    @Test
    void shouldHaveWorkoutSessionTable() throws Exception {
        try (Connection conn = dataSource.getConnection()) {
            assertThat(tableExists(conn, "workout_session")).isTrue();
        }
    }

    @Test
    void eventTableHasPrimaryKey() throws Exception {
        try (Connection conn = dataSource.getConnection()) {
            Set<String> cols = tableColumns(conn, "event");
            assertThat(cols).contains("id");
        }
    }

    @Test
    void userEventTableHasForeignKeyColumns() throws Exception {
        try (Connection conn = dataSource.getConnection()) {
            Set<String> cols = tableColumns(conn, "user_event");
            assertThat(cols).contains("id", "user_id", "event_id");
        }
    }

    @Test
    void workoutSessionTableHasTrainingForeignKey() throws Exception {
        try (Connection conn = dataSource.getConnection()) {
            Set<String> cols = tableColumns(conn, "workout_session");
            assertThat(cols).contains("id", "training_id");
        }
    }

    private boolean tableExists(Connection conn, String expectedName) throws SQLException {
        DatabaseMetaData meta = conn.getMetaData();
        try (ResultSet rs = meta.getTables(conn.getCatalog(), null, "%", new String[]{"TABLE"})) {
            while (rs.next()) {
                String schema = rs.getString("TABLE_SCHEM");
                if (schema == null) continue;
                if (!"PUBLIC".equalsIgnoreCase(schema)) continue;
                String name = rs.getString("TABLE_NAME");
                if (expectedName.equalsIgnoreCase(name)) {
                    return true;
                }
            }
        }
        return false;
    }

    private Set<String> tableColumns(Connection conn, String tableName) throws SQLException {
        DatabaseMetaData meta = conn.getMetaData();
        Set<String> cols = new HashSet<>();
        try (ResultSet rs = meta.getColumns(conn.getCatalog(), null, "%", "%")) {
            while (rs.next()) {
                String schema = rs.getString("TABLE_SCHEM");
                if (schema == null) continue;
                if (!"PUBLIC".equalsIgnoreCase(schema)) continue;
                String tbl = rs.getString("TABLE_NAME");
                if (!tableName.equalsIgnoreCase(tbl)) continue;
                String col = rs.getString("COLUMN_NAME");
                if (col != null) {
                    cols.add(col.toLowerCase());
                }
            }
        }
        return cols;
    }

    @Test
    void shouldFindUpcomingEvents() {
        Event past = new Event();
        past.setName("Stary event");
        past.setStartTime(LocalDateTime.now().minusDays(1));

        Event future = new Event();
        future.setName("Nadchodzący event");
        future.setStartTime(LocalDateTime.now().plusDays(5));

        eventRepository.save(past);
        eventRepository.save(future);

        List<Event> upcoming = eventRepository.findUpcoming(LocalDateTime.now());

        assertThat(upcoming).hasSize(1);
        assertThat(upcoming.get(0).getName()).isEqualTo("Nadchodzący event");
    }

    @Test
    void shouldReturnEventNamesWithParticipantCount() {
        Event e1 = new Event();
        e1.setName("Maraton Wrocław");

        Event e2 = new Event();
        e2.setName("Triathlon Gdańsk");

        eventRepository.save(e1);
        eventRepository.save(e2);

        List<EventParticipantCount> results = eventRepository.findEventNamesWithParticipantCount();

        assertThat(results).hasSize(2);
        assertThat(results).anyMatch(r -> "Maraton Wrocław".equals(r.getName()));
        assertThat(results).anyMatch(r -> "Triathlon Gdańsk".equals(r.getName()));
    }
}
