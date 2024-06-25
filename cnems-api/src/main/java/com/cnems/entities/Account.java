package com.cnems.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="account", schema = "public")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "name")
    String name;

    @Column(name ="balance")
    float balance;

    @Column(name = "user_id")
    Long userId;

    @Column(name = "created_at")
    Date createdAt;

    public Account(Long id, String name, float balance, Long userId, Date createdAt) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.userId = userId;
        this.createdAt = createdAt;
    }

    public Account(String name, float balance, Long userId, Date createdAt) {
        this.name = name;
        this.balance = balance;
        this.userId = userId;
        this.createdAt = createdAt;
    }

    public void changeBalance(float amount) {
        setBalance(getBalance() + amount);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
