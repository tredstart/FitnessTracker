package pl.wsb.fitnesstracker.user.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.wsb.fitnesstracker.user.api.UserDto;
import pl.wsb.fitnesstracker.user.api.UserNotFoundException;
import pl.wsb.fitnesstracker.user.api.UserProvider;
import pl.wsb.fitnesstracker.user.api.UserService;

import java.time.LocalDate;
import java.util.List;

/**
 * REST controller for user CRUD operations.
 */
@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserService userService;
    private final UserProvider userProvider;
    private final UserMapper userMapper;

    /** Full list — firstName, lastName, birthdate. */
    @GetMapping
    List<UserDto> getAllUsers() {
        return userProvider.findAllUsers().stream()
                .map(userMapper::toDto)
                .toList();
    }

    /** Simple list — id, firstName, lastName only. */
    @GetMapping("/simple")
    List<UserSimpleDto> getSimpleUsers() {
        return userProvider.findAllUsers().stream()
                .map(userMapper::toSimpleDto)
                .toList();
    }

    /** Full details for a single user by ID. */
    @GetMapping("/{id}")
    UserDto getUserById(@PathVariable Long id) {
        return userProvider.getUser(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    /** Case-insensitive email fragment search — returns id + email. */
    @GetMapping("/email")
    List<UserEmailDto> getUsersByEmail(@RequestParam String email) {
        return userProvider.findUsersByEmail(email).stream()
                .map(userMapper::toEmailDto)
                .toList();
    }

    /** Users born before the given date. */
    @GetMapping("/older/{time}")
    List<UserDto> getUsersOlderThan(@PathVariable LocalDate time) {
        return userProvider.findUsersOlderThan(time).stream()
                .map(userMapper::toDto)
                .toList();
    }

    /** Create a new user. */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    UserDto createUser(@RequestBody UserRequest request) {
        return userMapper.toDto(userService.createUser(userMapper.toEntity(request)));
    }

    /** Update an existing user. */
    @PutMapping("/{userId}")
    UserDto updateUser(@PathVariable Long userId, @RequestBody UserRequest request) {
        return userMapper.toDto(userService.updateUser(userId, userMapper.toEntity(request)));
    }

    /** Delete a user by ID. */
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }
}