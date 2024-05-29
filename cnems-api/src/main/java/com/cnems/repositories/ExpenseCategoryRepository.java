package com.cnems.repositories;

import com.cnems.entities.ExpenseCategory;
import com.cnems.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseCategoryRepository extends JpaRepository<ExpenseCategory, Long> {
    ExpenseCategory findByName(String name);
}
