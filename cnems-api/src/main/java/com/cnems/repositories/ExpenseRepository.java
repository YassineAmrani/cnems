package com.cnems.repositories;

import com.cnems.entities.Expense;
import com.cnems.entities.ExpenseCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseRepository  extends JpaRepository<Expense, Long> {
    List<Expense> findByCategoryId(Long categoryId);
}
