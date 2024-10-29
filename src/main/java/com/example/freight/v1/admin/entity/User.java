package com.example.freight.v1.admin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;



@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    private Long id;

    @Column(name = "email", length = 100, unique = true)
    private String email;

    @Column(name = "first_name", length = 50)
    private String firstName;

    @Column(name = "last_name", length = 50)
    private String lastName;

//    @OneToMany(mappedBy = "user")
//    private List<UserLanguage> languages;

    @Column(name = "title")
    private String title;

    private String userRole;

}
