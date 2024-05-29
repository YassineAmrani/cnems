package com.cnems.services;

import com.cnems.entities.Expense;
import com.cnems.entities.ExpenseCategory;
import com.cnems.exceptions.CnemsException;
import com.cnems.repositories.ExpenseCategoryRepository;
import com.cnems.repositories.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ExpenseCategoryService {

    @Autowired
    private ExpenseCategoryRepository expenseCategoryRepository;

    @Autowired
    private ExpenseRepository expenseRepository;


    public ExpenseCategory getCategory(Long id) throws CnemsException{
        try {
            Optional<ExpenseCategory> expenseCategory = expenseCategoryRepository.findById(id);

            if(expenseCategory.isEmpty()) throw new CnemsException(404, "Expense Category Not Found");

            return expenseCategory.get();
        } catch(Exception e) {
            throw new CnemsException(500, "Something went wrong");
        }
    }

    public List<ExpenseCategory> getCategories() throws CnemsException{
        try {
            return expenseCategoryRepository.findAll();
        } catch(Exception e) {
            throw new CnemsException(500, "Something went wrong");
        }
    }

    public void addCategory(String name, String description) throws CnemsException{
        ExpenseCategory expenseCategory = expenseCategoryRepository.findByName(name);
        if(expenseCategory != null && name.equals(expenseCategory.getName())) throw new CnemsException(400, "Category Already Exists");
        ExpenseCategory category = new ExpenseCategory(name, description);
        expenseCategoryRepository.save(category);
    }

    public void updateCategory(Long id, String name, String description) throws CnemsException {
        Optional<ExpenseCategory> category = expenseCategoryRepository.findById(id);

        ExpenseCategory checkName = expenseCategoryRepository.findByName(name);

        if(category.isEmpty()) throw new CnemsException(404, "Category Not Found");
        if(checkName != null && !Objects.equals(checkName.getId(), category.get().getId())) throw new CnemsException(400, "Category Already Exists");

        category.get().setName(name);
        category.get().setDescription(description);

        expenseCategoryRepository.save(category.get());
    }

    public void deleteCategory(Long id) throws CnemsException {
        Optional<ExpenseCategory> category = expenseCategoryRepository.findById(id);
        if(category.isEmpty()) throw new CnemsException(404, "Category Not Found");

        boolean isRelated = expenseRepository.findByCategoryId(id).isEmpty();

        if(!isRelated) throw new CnemsException(400, "Category is linked to Expenses");

        expenseCategoryRepository.delete(category.get());
    }
}
