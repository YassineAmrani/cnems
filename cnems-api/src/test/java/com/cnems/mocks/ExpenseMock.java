package com.cnems.mocks;

import com.cnems.entities.Expense;

import java.util.Collections;
import java.util.List;

public class ExpenseMock {

    public static List<Expense> getExpenseListMock() {
        return Collections.singletonList(new Expense());
    }
}
