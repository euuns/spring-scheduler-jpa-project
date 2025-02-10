package com.example.scheduleserver.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class User extends DateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    public User() {
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public void updateInfo(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
