package com.cnems.dto;

import java.util.List;

public class UserSummaryDTO {

    float totalBalance;

    float totalSpent;

    List<SumExpensesByAccount> accountExpenseSummary;

    List<SumExpenseByCategory> categoryExpenseSummary;

    public UserSummaryDTO(float totalBalance, float totalSpent, List<SumExpensesByAccount> accountExpenseSummary, List<SumExpenseByCategory> categoryExpenseSummary) {
        this.totalBalance = totalBalance;
        this.totalSpent = totalSpent;
        this.accountExpenseSummary = accountExpenseSummary;
        this.categoryExpenseSummary = categoryExpenseSummary;
    }

    public float getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(float totalBalance) {
        this.totalBalance = totalBalance;
    }

    public float getTotalSpent() {
        return totalSpent;
    }

    public void setTotalSpent(float totalSpent) {
        this.totalSpent = totalSpent;
    }

    public List<SumExpensesByAccount> getAccountExpenseSummary() {
        return accountExpenseSummary;
    }

    public void setAccountExpenseSummary(List<SumExpensesByAccount> accountExpenseSummary) {
        this.accountExpenseSummary = accountExpenseSummary;
    }

    public List<SumExpenseByCategory> getCategoryExpenseSummary() {
        return categoryExpenseSummary;
    }

    public void setCategoryExpenseSummary(List<SumExpenseByCategory> categoryExpenseSummary) {
        this.categoryExpenseSummary = categoryExpenseSummary;
    }
}
