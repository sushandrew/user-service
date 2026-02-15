package com.sushkov.domain;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String email;

    private int age;

    @Column(name = "created_at")
    private LocalDate createdAt;

    public User() {}

    public User(int id, String name, String email, int age, LocalDate createdAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.createdAt = createdAt;
    }

    public User(String name, String email, int age, LocalDate createdAt) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return id + ") " +
                name + "|" +
                email + "|" +
                age + " лет|" +
                "создано " + createdAt;
    }
}
