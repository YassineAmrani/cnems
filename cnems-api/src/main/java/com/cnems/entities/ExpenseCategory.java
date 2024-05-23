package com.cnems.entities;

import jakarta.persistence.*;

@Entity
@Table(name="expense_categories", schema = "public")
public class ExpenseCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "name")
    String name;

    @Column(name ="description")
    String description;

    public ExpenseCategory(Long id, String name, String description) {
        setId(id);
        setName(name);
        setDescription(description);
    }

    public ExpenseCategory(String name, String description) {
        setName(name);
        setDescription(description);
    }

    public ExpenseCategory() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
