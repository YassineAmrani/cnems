package com.cnems.services;

import com.cnems.entities.ExpenseCategory;
import com.cnems.exceptions.CnemsException;
import com.cnems.mocks.ExpenseCategoryMock;
import com.cnems.mocks.ExpenseMock;
import com.cnems.mocks.UserMocks;
import com.cnems.repositories.ExpenseCategoryRepository;
import com.cnems.repositories.ExpenseRepository;
import com.cnems.repositories.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ExpenseServiceTest {

    @InjectMocks
    ExpenseService expenseService;

    @Mock
    ExpenseRepository expenseRepository;

    @Mock
    ExpenseCategoryRepository expenseCategoryRepository;

    @Mock
    UserRepository userRepository;

    @Test
    @DisplayName("Test Get Expense")
    public void testGetExpense() {
        when(expenseRepository.findById(any())).thenReturn(Optional.of(ExpenseMock.getExpenseMock()));
        when(expenseCategoryRepository.findById(any())).thenReturn(Optional.of(ExpenseCategoryMock.getCategoryMock()));

        assertDoesNotThrow(() -> expenseService.getExpense(0L));
    }

    @Test
    @DisplayName("Test Get Expense Not Found")
    public void testGetExpenseNotFound() {
        when(expenseRepository.findById(any())).thenReturn(Optional.empty());

        CnemsException cnemsException = assertThrows(CnemsException.class, () -> expenseService.getExpense(0L));
        assertEquals(cnemsException.getStatus(), 404);
    }

    @Test
    @DisplayName("Test Get Expense Category Not Found")
    public void testGetExpenseCategoryNotFound() {
        when(expenseRepository.findById(any())).thenReturn(Optional.of(ExpenseMock.getExpenseMock()));
        when(expenseCategoryRepository.findById(any())).thenReturn(Optional.empty());

        CnemsException cnemsException = assertThrows(CnemsException.class, () -> expenseService.getExpense(0L));
        assertEquals(cnemsException.getStatus(), 404);
    }

    @Test
    @DisplayName("Test Add Expense")
    public void testAddExpense() {
        when(userRepository.findById(any())).thenReturn(Optional.of(UserMocks.getMockedUser()));
        when(expenseCategoryRepository.findById(any())).thenReturn(Optional.of(ExpenseCategoryMock.getCategoryMock()));

        when(expenseRepository.save(any())).thenReturn(ExpenseMock.getExpenseMock());

        assertDoesNotThrow(() -> expenseService.addExpense(0L, 0L, 25, new Date(), ""));
    }

    @Test
    @DisplayName("Test Add Expense amount equals zero")
    public void testAddExpenseAmountEquals() {

        CnemsException cnemsException = assertThrows(CnemsException.class, () -> expenseService.addExpense(0L, 0L, 0, new Date(), ""));
        assertEquals(cnemsException.getStatus(), 400);
    }

    @Test
    @DisplayName("Test Add Expense Date After Today")
    public void testAddExpenseDateAfterNow() {

        CnemsException cnemsException = assertThrows(CnemsException.class, () -> expenseService.addExpense(0L, 0L, 25, new Date(2024, 6,10), ""));
        assertEquals(cnemsException.getStatus(), 400);
    }

    @Test
    @DisplayName("Test Add Expense user not found")
    public void testAddCategoryUserNotFound() {

        when(userRepository.findById(any())).thenReturn(Optional.empty());
        when(expenseCategoryRepository.findById(any())).thenReturn(Optional.of(ExpenseCategoryMock.getCategoryMock()));

        CnemsException cnemsException = assertThrows(CnemsException.class, () -> expenseService.addExpense(0L, 0L, 25, new Date(), ""));
        assertEquals(cnemsException.getStatus(), 404);
    }
}