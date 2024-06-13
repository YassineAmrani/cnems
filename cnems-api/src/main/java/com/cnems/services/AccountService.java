package com.cnems.services;

import com.cnems.entities.Account;
import com.cnems.entities.Expense;
import com.cnems.exceptions.CnemsException;
import com.cnems.repositories.AccountRepository;
import com.cnems.repositories.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ExpenseRepository expenseRepository;

    public Account getAccount(Long accountId) throws CnemsException {
        Optional<Account> account = accountRepository.findById(accountId);

        if(account.isEmpty()) throw new CnemsException(404, "Account not found");

        return account.get();
    }

    public List<Account> getAccountsByUser(Long userId) {

        return accountRepository.findByUserId(userId);
    }

    public void deleteAccount(Long accountId) throws CnemsException {
        Optional<Account> account = accountRepository.findById(accountId);

        if(account.isEmpty()) throw new CnemsException(404, "Account not found");

        List<Expense> expenseList = expenseRepository.findByAccountIdOrderByDateDesc(accountId);

        expenseRepository.deleteAll(expenseList);

        accountRepository.delete(account.get());
    }


}
