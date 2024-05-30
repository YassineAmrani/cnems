package com.cnems.services;

import com.cnems.dto.ExpenseResponse;
import com.cnems.entities.Expense;
import com.cnems.entities.ExpenseCategory;
import com.cnems.exceptions.CnemsException;
import com.cnems.repositories.ExpenseCategoryRepository;
import com.cnems.repositories.ExpenseRepository;
import com.cnems.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class ExpenseService {

    @Autowired
    ExpenseRepository expenseRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ExpenseCategoryRepository expenseCategoryRepository;

    public ExpenseResponse getExpense(Long id) throws CnemsException {
        Optional<Expense> expense = expenseRepository.findById(id);

        if(expense.isEmpty()) throw new CnemsException(404, "Expense not found");

        Optional<ExpenseCategory> expenseCategory = expenseCategoryRepository.findById(expense.get().getCategoryId());

        if(expenseCategory.isEmpty()) throw new CnemsException(404, "Expense Category not found");

        return new ExpenseResponse(expense.get(), expenseCategory.get());
    }

    public void addExpense(Long userId, Long categoryId, float amount, Date date, String description) throws CnemsException {

        if(amount == 0) throw new CnemsException(400, "Amount can't be zero");
        if(date.after(new Date())) throw new CnemsException(400, "Date can't be in the future");
        if(userRepository.findById(userId).isEmpty()) throw new CnemsException(404, "User not found, please contact support");
        if(expenseCategoryRepository.findById(categoryId).isEmpty()) throw new CnemsException(404, "Category not found, please create it first");

        Expense expense = new Expense(userId, categoryId, amount, date, description, new Date());

        expenseRepository.save(expense);
    }

    public void updateExpense(Long id, Long categoryId, float amount, Date date, String description) throws CnemsException {

        if(amount == 0) throw new CnemsException(400, "Amount can't be zero");
        if(date.after(new Date())) throw new CnemsException(400, "Date can't be in the future");
        if(expenseCategoryRepository.findById(categoryId).isEmpty()) throw new CnemsException(404, "Category not found, please create it first");

        Optional<Expense> optionalExpense = expenseRepository.findById(id);

        if(optionalExpense.isEmpty()) throw new CnemsException(404, "Expense not found, please create it first");

        Expense expense = optionalExpense.get();

        expense.setDate(date);
        expense.setAmount(amount);
        expense.setCategoryId(categoryId);
        expense.setDescription(description);

        expenseRepository.save(expense);
    }

    public void deleteExpense(Long id) throws CnemsException {
        Optional<Expense> optionalExpense = expenseRepository.findById(id);
        if(optionalExpense.isEmpty()) throw new CnemsException(404, "Expense not found, please create it first");

        expenseRepository.delete(optionalExpense.get());
    }
}
