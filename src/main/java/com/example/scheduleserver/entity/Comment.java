package com.example.scheduleserver.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class Comment extends DateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Schedule schedule;

    @Column(nullable = false, length = 100)
    private String contents;


    public Comment() {
    }

    public Comment(Schedule schedule, User user, String contents) {
        this.schedule = schedule;
        this.user = user;
        this.contents = contents;
    }

    public void update(String contents) {
        this.contents = contents;
    }
}
