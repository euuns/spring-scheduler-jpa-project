package com.example.scheduleserver.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class Schedule extends DateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String title;

    @Column(length = 500)
    private String contents;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private User user;

    public Schedule() {
    }

    public Schedule(String title, String contents, User user) {
        this.title = title;
        this.contents = contents;
        this.user = user;
    }

    public void update(String updateTitle, String updateContents) {
        this.title = updateTitle;
        this.contents = updateContents;
    }
}
