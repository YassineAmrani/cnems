package com.cnems.services;

import com.cnems.mocks.SummaryMock;
import com.cnems.repositories.AccountRepository;
import com.cnems.repositories.ExpenseRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class SummaryServiceTest {

    @InjectMocks
    SummaryService summaryService;

    @Mock
    SummaryService summaryServiceMock;

    @Mock
    ExpenseRepository expenseRepository;

    @Mock
    AccountRepository accountRepository;

    @Test
    @DisplayName("Test Get Summary By Account")
    public void testGetSummaryByAccount() {
        when(expenseRepository.getSumOfExpensesByAccountBetweenDate(any(), any(), any()))
                .thenReturn(List.of(SummaryMock.getSumExpensesByAccountMock()));

        assertDoesNotThrow(() -> summaryService.getExpenseSummaryByAccountBetweenDates(0L, new Date(), new Date()));
    }

    @Test
    @DisplayName("Test Get Summary By Category")
    public void testGetSummaryByCategory() {
        when(expenseRepository.getSumOfExpensesByCategoryBetweenDate(any(), any(), any()))
                .thenReturn(List.of(SummaryMock.getSumExpensesByCategoryMock()));

        assertDoesNotThrow(() -> summaryService.getExpenseSummaryByCategoryBetweenDates(0L, new Date(), new Date()));
    }

    @Test
    @DisplayName("Test Get Total Spent")
    public void testGetTotalSpent() {
        when(expenseRepository.getTotalSpentBetweenDates(any(), any(), any()))
                .thenReturn(10f);

        assertDoesNotThrow(() -> summaryService.getTotalSpentBetweenDates(0L, new Date(), new Date()));
    }

    @Test
    @DisplayName("Test Get Total Balance")
    public void testGetTotalBalance() {
        when(accountRepository.getTotalBalance(any()))
                .thenReturn(10f);

        assertDoesNotThrow(() -> summaryService.getTotalBalance(0L));
    }

    @Test
    @DisplayName("Test Get Summary By Category")
    public void testGetFullSummary() {
        when(summaryService.getExpenseSummaryByCategoryBetweenDates(any(), any(), any()))
                .thenReturn(List.of(SummaryMock.getSumExpensesByCategoryMock()));
        when(summaryService.getExpenseSummaryByAccountBetweenDates(any(), any(), any()))
                .thenReturn(List.of(SummaryMock.getSumExpensesByAccountMock()));
        when(summaryService.getTotalBalance(any())).thenReturn(10f);
        when(summaryService.getTotalBalance(any())).thenReturn(10f);

        assertDoesNotThrow(() -> summaryService.getFullSummaryBetweenDates(0L, new Date(), new Date()));
    }
}
