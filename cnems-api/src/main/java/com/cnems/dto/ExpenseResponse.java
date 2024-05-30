package com.cnems.dto;

import com.cnems.entities.Expense;
import com.cnems.entities.ExpenseCategory;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Date;

public class ExpenseResponse {
    Long id;

    Long userId;

    ExpenseCategory category;

    float amount;

    Date date;

    String description;

    Date createdAt;

    public ExpenseResponse(Expense expense, ExpenseCategory category) {
        setId(expense.getId());
        setUserId(expense.getUserId());
        setAmount(expense.getAmount());
        setDate(expense.getDate());
        setDescription(expense.getDescription());
        setCreatedAt(expense.getCreatedAt());
        setCategory(category);
    }

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

    public ExpenseCategory getCategory() {
        return category;
    }

    public void setCategory(ExpenseCategory category) {
        this.category = category;
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
}
