package com.example.freight.v1.admin.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/admin", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminController {

    @GetMapping
    public void createUser() {
    }

    @GetMapping
    public void getUsers() {
    }

    @DeleteMapping("/{id}")
    public void deleteUser() {
    }

    @PutMapping("/{id}")
    public void updateUser() {
    }
}
