package com.example.freight.v1.admin.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    private String id;
    private String email;
    private String firstName;
    private String lastName;
    //    private List<String> languages;
    private String title;
    private String userRole;

}
