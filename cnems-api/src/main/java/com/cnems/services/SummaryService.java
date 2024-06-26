package com.cnems.services;

import com.cnems.dto.SumExpenseByCategory;
import com.cnems.dto.SumExpensesByAccount;
import com.cnems.dto.UserSummaryDTO;
import com.cnems.repositories.AccountRepository;
import com.cnems.repositories.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SummaryService {

    @Autowired
    ExpenseRepository expenseRepository;

    @Autowired
    AccountRepository accountRepository;

    public List<SumExpensesByAccount> getExpenseSummaryByAccountBetweenDates(Long userId, Date startDate, Date endDate) {
        return expenseRepository.getSumOfExpensesByAccountBetweenDate(userId, startDate, endDate);
    }

    public List<SumExpenseByCategory> getExpenseSummaryByCategoryBetweenDates(Long userId, Date startDate, Date endDate) {
        return expenseRepository.getSumOfExpensesByCategoryBetweenDate(userId, startDate, endDate);
    }

    public float getTotalSpentBetweenDates(Long userId, Date startDate, Date endDate) {
        return expenseRepository.getTotalSpentBetweenDates(userId, startDate, endDate);
    }

    public float getTotalBalance(Long userId) {
        return accountRepository.getTotalBalance(userId);
    }

    public UserSummaryDTO getFullSummaryBetweenDates(Long userId, Date startDate, Date endDate) {
        return new UserSummaryDTO(
                getTotalBalance(userId),
                getTotalSpentBetweenDates(userId, startDate, endDate),
                getExpenseSummaryByAccountBetweenDates(userId, startDate, endDate),
                getExpenseSummaryByCategoryBetweenDates(userId, startDate, endDate));
    }
}
