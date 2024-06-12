package com.cnems.repositories;

import com.cnems.entities.Expense;
import com.cnems.entities.ExpenseCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseRepository  extends JpaRepository<Expense, Long> {
    Page<Expense> findByCategoryId(Long categoryId, Pageable pageable);
    List<Expense> findByCategoryId(Long categoryId);
    Page<Expense> findByUserId(Long userId, Pageable pageable);
}
