package com.cnems.entities;

import jakarta.persistence.*;

@Entity
@Table(name="users", schema = "public")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    public User(long id, String username, String password) {
        setId(id);
        setUsername(username);
        setPassword(password);
    }

    public User(String username, String password) {
        setUsername(username);
        setPassword(password);
    }

    public long getId() {
        return id;
    }

    public void setId(long identifier) {
        id = identifier;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
