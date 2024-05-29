package com.cnems.mocks;

import com.cnems.entities.Expense;
import com.cnems.entities.ExpenseCategory;

import java.util.Collections;
import java.util.List;

import static java.util.Collections.EMPTY_LIST;

public class ExpenseCategoryMock {

    public static ExpenseCategory getCategoryMock() {
        return new ExpenseCategory(0L, "Test Category", "Test Description");
    }

    public static List<ExpenseCategory> getAllCategoriesMock() {
        return EMPTY_LIST;
    }
}
