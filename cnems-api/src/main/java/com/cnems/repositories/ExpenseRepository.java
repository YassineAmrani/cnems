package com.cnems.repositories;

import com.cnems.dto.SumExpenseByCategory;
import com.cnems.dto.SumExpensesByAccount;
import com.cnems.entities.Expense;
import com.cnems.entities.ExpenseCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface ExpenseRepository  extends JpaRepository<Expense, Long> {
    Page<Expense> findByCategoryIdOrderByDateDesc(Long categoryId, Pageable pageable);
    List<Expense> findByCategoryIdOrderByDateDesc(Long categoryId);
    Page<Expense> findByUserIdOrderByDateDesc(Long userId, Pageable pageable);
    List<Expense> findByAccountIdOrderByDateDesc(Long accountId);

    Page<Expense> findByAccountIdOrderByDateDesc(Long accountId, Pageable pageable);

    @Query(value = "SELECT account_id, SUM(amount) as sum_amount FROM public.expenses WHERE user_id = ?1 AND date BETWEEN ?2 AND ?3 GROUP BY account_id",
    nativeQuery = true)
    List<SumExpensesByAccount> getSumOfExpensesByAccountBetweenDate(Long userId, Date startDate, Date endDate);

    @Query(value = "SELECT category_id, SUM(amount) as sum_amount FROM public.expenses WHERE user_id = ?1 AND date BETWEEN ?2 AND ?3 GROUP BY category_id",
            nativeQuery = true)
    List<SumExpenseByCategory> getSumOfExpensesByCategoryBetweenDate(Long userId, Date startDate, Date endDate);

    @Query(value = "SELECT SUM(amount) FROM public.expenses WHERE user_id = ?1 AND date BETWEEN ?2 AND ?3",
            nativeQuery = true)
    float getTotalSpentBetweenDates(Long userId, Date startDate, Date endDate);
}
