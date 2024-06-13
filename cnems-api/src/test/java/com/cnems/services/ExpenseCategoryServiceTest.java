package com.cnems.services;

import com.cnems.entities.Expense;
import com.cnems.entities.ExpenseCategory;
import com.cnems.exceptions.CnemsException;
import com.cnems.mocks.ExpenseCategoryMock;
import com.cnems.mocks.ExpenseMock;
import com.cnems.repositories.ExpenseCategoryRepository;
import com.cnems.repositories.ExpenseRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import static java.util.Collections.EMPTY_LIST;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ExpenseCategoryServiceTest {

    @InjectMocks
    ExpenseCategoryService expenseCategoryService;

    @Mock
    ExpenseCategoryRepository expenseCategoryRepository;

    @Mock
    ExpenseRepository expenseRepository;

    @Test
    @DisplayName("Test Get One Category")
    public void testGetOneCategory() {
        when(expenseCategoryRepository.findById(any())).thenReturn(Optional.of(ExpenseCategoryMock.getCategoryMock()));

        assertDoesNotThrow(() -> expenseCategoryService.getCategory(0L));
    }

    @Test
    @DisplayName("Test Get One Category not found")
    public void testGetOneCategoryThrows404() {
        when(expenseCategoryRepository.findById(any())).thenReturn(Optional.empty());

        CnemsException cnemsException = assertThrows(CnemsException.class, () -> expenseCategoryService.getCategory(0L));
        //assertEquals(cnemsException.getStatus(), 404);
    }

    @Test
    @DisplayName("Test Get All Categories")
    public void testGetAllCategories() {
        when(expenseCategoryRepository.findAll()).thenReturn(ExpenseCategoryMock.getAllCategoriesMock());

        assertDoesNotThrow(() -> expenseCategoryService.getCategories());
    }

    @Test
    @DisplayName("Test Add Category")
    public void testAddCategory() {
        when(expenseCategoryRepository.findByName(any())).thenReturn(null);

        when(expenseCategoryRepository.save(any())).thenReturn(ExpenseCategoryMock.getCategoryMock());

        assertDoesNotThrow(() -> expenseCategoryService.addCategory("test", "test"));
    }

    @Test
    @DisplayName("Test Add Category already exists")
    public void testAddCategoryAlreadyExists() {
        when(expenseCategoryRepository.findByName(any())).thenReturn(ExpenseCategoryMock.getCategoryMock());

        CnemsException cnemsException = assertThrows(CnemsException.class, () -> expenseCategoryService.addCategory("Test Category", "test"));

        assertEquals(cnemsException.getStatus(), 400);
    }

    @Test
    @DisplayName("Test Update Category")
    public void testUpdateCategory() {
        when(expenseCategoryRepository.findById(any())).thenReturn(Optional.of(ExpenseCategoryMock.getCategoryMock()));
        when(expenseCategoryRepository.findByName(any())).thenReturn(null);

        when(expenseCategoryRepository.save(any())).thenReturn(ExpenseCategoryMock.getCategoryMock());

        assertDoesNotThrow(() -> expenseCategoryService.updateCategory(0L, "Test Bategory", "Test Bescriptioon"));
    }

    @Test
    @DisplayName("Test Update Category Does not exist")
    public void testUpdateCategoryDoesNotExist() {
        when(expenseCategoryRepository.findById(any())).thenReturn(Optional.empty());

        CnemsException cnemsException = assertThrows(CnemsException.class, () -> expenseCategoryService.updateCategory(0L, "Test Bategory", "Test Bescriptioon"));
        assertEquals(cnemsException.getStatus(), 404);
    }


    @Test
    @DisplayName("Test Update Category Name already exists")
    public void testUpdateCategoryNameAlreadyExists() {
        when(expenseCategoryRepository.findById(any())).thenReturn(Optional.of(ExpenseCategoryMock.getCategoryMock()));
        when(expenseCategoryRepository.findByName(any())).thenReturn(new ExpenseCategory(1L, "Test Bategory", "Test Bescriptioon"));

        CnemsException cnemsException = assertThrows(CnemsException.class, () -> expenseCategoryService.updateCategory(0L, "Test Bategory", "Test Bescriptioon"));
        assertEquals(cnemsException.getStatus(), 400);
    }

    @Test
    @DisplayName("Test Delete Category")
    public void testDeleteCategory() {
        when(expenseCategoryRepository.findById(any())).thenReturn(Optional.of(ExpenseCategoryMock.getCategoryMock()));
        when(expenseRepository.findByCategoryIdOrderByDateDesc(any())).thenReturn(EMPTY_LIST);

        doNothing().when(expenseCategoryRepository).delete(any());

        assertDoesNotThrow(() -> expenseCategoryService.deleteCategory(0L));
    }

    @Test
    @DisplayName("Test Delete Category not found")
    public void testDeleteCategoryNotFound() {
        when(expenseCategoryRepository.findById(any())).thenReturn(Optional.empty());

        CnemsException cnemsException = assertThrows(CnemsException.class, () -> expenseCategoryService.deleteCategory(0L));

        assertEquals(cnemsException.getStatus(), 404);
    }

    @Test
    @DisplayName("Test Delete Category is related")
    public void testDeleteCategoryIsRelated() {
        when(expenseCategoryRepository.findById(any())).thenReturn(Optional.of(ExpenseCategoryMock.getCategoryMock()));
        when(expenseRepository.findByCategoryIdOrderByDateDesc(any())).thenReturn(ExpenseMock.getExpenseListMock());

        CnemsException cnemsException = assertThrows(CnemsException.class, () -> expenseCategoryService.deleteCategory(0L));

        assertEquals(cnemsException.getStatus(), 400);
    }
}
