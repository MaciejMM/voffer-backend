package com.example.freight.v1.admin.controller;

import com.example.freight.v1.admin.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/admin", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminController {

    private final UserService userService;

    public AdminController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public void createUser() {
        userService.createUser();
    }

    @GetMapping(path = "/users")
    public void getUsers() {
        userService.getUsers();
    }

    @PutMapping("/{id}")
    public void updateUser(final @PathVariable String id) {
        userService.updateUser(id);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(final @PathVariable String id) {
        userService.deleteUser(id);
    }
}
