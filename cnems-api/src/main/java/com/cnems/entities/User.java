package com.cnems.entities;

import jakarta.persistence.*;

import java.util.Date;

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

    @Column(name = "email")
    private String email;

    @Column(name = "role")
    private String role;

    @Column(name = "created_at")
    private Date createdAt;

    public User(long id, String username, String password, String email, String role, Date createdAt) {
        setId(id);
        setUsername(username);
        setPassword(password);
        setEmail(email);
        setRole(role);
        setCreatedAt(createdAt);
    }

    public User(String username, String password, String email, String role, Date createdAt) {
        setUsername(username);
        setPassword(password);
        setEmail(email);
        setRole(role);
        setCreatedAt(createdAt);
    }

    public User() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
