package com.example.freight.v1.admin;

import com.example.freight.auth.models.entity.User;
import com.example.freight.auth.models.request.UserRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "api/v1/admin", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminController {

    private static final String BODY_MESSAGE = "Message";
    private final UserService userService;

    public AdminController(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody final UserRequest userRequest) {
        final User user = userService.createUser(userRequest);
        return ResponseEntity.ok().body(user);
    }


    @GetMapping(path = "/users")
    public ResponseEntity<List<User>> getUsers() {
        final List<User> users = userService.getUsers();
        return ResponseEntity.ok().body(users);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> updateUser(final @PathVariable Long id, final @RequestBody UserUpdateRequest userRequest) {
        userService.updateUser(id, userRequest);
        return ResponseEntity.ok().body(Map.of(BODY_MESSAGE, "User updated successfully"));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteUser(final @PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().body(Map.of(BODY_MESSAGE, "User deleted successfully"));
    }

}
