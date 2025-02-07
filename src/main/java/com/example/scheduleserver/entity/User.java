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

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, length = 20)
    private String password;
}
