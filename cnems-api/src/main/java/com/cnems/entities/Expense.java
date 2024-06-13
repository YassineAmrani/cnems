package com.cnems.entities;

import jakarta.persistence.*;
import org.springframework.lang.NonNull;

import java.util.Date;

@Entity
@Table(name="expenses", schema = "public")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "user_id")
    Long userId;

    @Column(name = "category_id")
    Long categoryId;

    @Column(name = "amount")
    float amount;

    @Column(name = "date")
    Date date;

    @Column(name = "account_id")
    Long accountId;

    @Column(name = "description")
    String description;

    @Column(name = "created_at")
    Date createdAt;

    public Expense(Long id, Long userId, Long categoryId, float amount, Date date, String description, Date createdAt, Long accountId) {
        setId(id);
        setUserId(userId);
        setCategoryId(categoryId);
        setAmount(amount);
        setDate(date);
        setDescription(description);
        setCreatedAt(createdAt);
        setAccountId(accountId);
    }

    public Expense(Long userId, Long categoryId, float amount, Date date, String description, Date createdAt, Long accountId) {
        setUserId(userId);
        setCategoryId(categoryId);
        setAmount(amount);
        setDate(date);
        setDescription(description);
        setCreatedAt(createdAt);
        setAccountId(accountId);
    }

    public Expense() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
}
