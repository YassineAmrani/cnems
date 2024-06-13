package com.cnems.services;

import com.cnems.entities.Account;
import com.cnems.entities.Expense;
import com.cnems.entities.ExpenseCategory;
import com.cnems.entities.User;
import com.cnems.exceptions.CnemsException;
import com.cnems.repositories.AccountRepository;
import com.cnems.repositories.ExpenseCategoryRepository;
import com.cnems.repositories.ExpenseRepository;
import com.cnems.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {

    @Autowired
    ExpenseRepository expenseRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ExpenseCategoryRepository expenseCategoryRepository;

    public Expense getExpense(Long id) throws CnemsException {
        Optional<Expense> expense = expenseRepository.findById(id);

        if(expense.isEmpty()) throw new CnemsException(404, "Expense not found");

        return expense.get();
    }

    public List<Expense> getByCategory(Long categoryId, int page) throws CnemsException {
        Optional<ExpenseCategory> category = expenseCategoryRepository.findById(categoryId);

        if(category.isEmpty()) throw new CnemsException(404, "Expense Category not found");

        Pageable pageable = PageRequest.of(page, 5);

        return expenseRepository.findByCategoryIdOrderByDateDesc(categoryId, pageable).toList();
    }

    public  List<Expense> getByUserId(Long userId, int page) throws CnemsException {
        Pageable pageable = PageRequest.of(page, 5);
        return expenseRepository.findByUserIdOrderByDateDesc(userId, pageable).toList();
    }

    public  List<Expense> getByAccountId(Long accountId, int page) throws CnemsException {
        Pageable pageable = PageRequest.of(page, 5);
        return expenseRepository.findByAccountIdOrderByDateDesc(accountId, pageable).toList();
    }

    public void addExpense(Long userId, Long categoryId, float amount, Date date, String description, Long accountId) throws CnemsException {

        if(amount == 0) throw new CnemsException(400, "Amount can't be zero");
        if(date.after(new Date())) throw new CnemsException(400, "Date can't be in the future");

        Optional<ExpenseCategory> category = expenseCategoryRepository.findById(categoryId);

        Optional<Account> account = accountRepository.findById(accountId);

        if(category.isEmpty()) throw new CnemsException(404, "Category not found, please create it first");
        if(account.isEmpty()) throw new CnemsException(404, "Account not found, please create it first");

        account.get().changeBalance(amount);

        Expense expense = new Expense(userId, categoryId, amount, date, description, new Date(), accountId);

        expenseRepository.save(expense);

        accountRepository.save(account.get());
    }

    public void updateExpense(Long id, Long categoryId, float amount, Date date, String description, Long accountId) throws CnemsException {

        if(amount == 0) throw new CnemsException(400, "Amount can't be zero");
        if(date.after(new Date())) throw new CnemsException(400, "Date can't be in the future");

        Optional<ExpenseCategory> category = expenseCategoryRepository.findById(categoryId);
        Optional<Account> account = accountRepository.findById(accountId);
        Optional<Expense> optionalExpense = expenseRepository.findById(id);


        if(category.isEmpty()) throw new CnemsException(404, "Category not found, please create it first");
        if(optionalExpense.isEmpty()) throw new CnemsException(404, "Expense not found, please create it first");
        if(account.isEmpty()) throw new CnemsException(404, "Account not found, please create it first");

        Expense expense = optionalExpense.get();

        if(expense.getAccountId() != account.get().getId())
        {
            Optional<Account> OldAccount = accountRepository.findById(expense.getAccountId());
            if(OldAccount.isEmpty()) throw new CnemsException(404, "Old Account not found, please create it first");
            OldAccount.get().changeBalance(-expense.getAmount());
            accountRepository.save(OldAccount.get());
        }

        expense.setDate(date);
        expense.setAmount(amount);
        expense.setCategoryId(categoryId);
        expense.setDescription(description);

        account.get().changeBalance(-amount);

        account.get().changeBalance(amount);
        accountRepository.save(account.get());

        expenseRepository.save(expense);
    }

    public void deleteExpense(Long id) throws CnemsException {
        Optional<Expense> optionalExpense = expenseRepository.findById(id);
        if(optionalExpense.isEmpty()) throw new CnemsException(404, "Expense not found, please create it first");

        expenseRepository.delete(optionalExpense.get());
    }
}
