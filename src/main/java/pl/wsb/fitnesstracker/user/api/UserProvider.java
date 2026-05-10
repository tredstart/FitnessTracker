package pl.wsb.fitnesstracker.user.api;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserProvider {

    Optional<User> getUser(Long userId);

    Optional<User> getUserByEmail(String email);

    List<User> findAllUsers();

    /** Case-insensitive fragment search on email. */
    List<User> findUsersByEmail(String emailFragment);

    /** Users born before the given date (i.e. older than that date). */
    List<User> findUsersOlderThan(LocalDate date);
}