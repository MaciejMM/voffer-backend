package com.example.freight.v1.admin.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "user_language")
public class UserLanguage {

    @Id
    private String id;
    private Long userLanguageId;
    private String roleName;
}
