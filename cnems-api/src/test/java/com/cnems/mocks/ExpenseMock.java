package com.cnems.mocks;

import com.cnems.entities.Expense;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ExpenseMock {

    public static List<Expense> getExpenseListMock() {
        return Collections.singletonList(new Expense());
    }

    public static Expense getExpenseMock() {return new Expense(0L, 0L, 0L, 25f, new Date(), "",new Date());}
}
