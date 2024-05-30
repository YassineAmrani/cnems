package com.cnems.endpoints;

import com.cnems.dto.SuccessMessage;
import com.cnems.entities.ExpenseCategory;
import com.cnems.exceptions.CnemsException;
import com.cnems.services.ExpenseCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class ExpenseCategoryEndpoint {

    @Autowired
    ExpenseCategoryService expenseCategoryService;

    @GetMapping("/{id}")
    ResponseEntity<ExpenseCategory> getCategory(@PathVariable("id") Long id) {
        try {
            ExpenseCategory expenseCategory = expenseCategoryService.getCategory(id);

            return ResponseEntity.ok(expenseCategory);
        } catch(CnemsException e) {
            return ResponseEntity.status(e.getStatus()).build();
        }
    }

    @GetMapping("/fetchAll")
    ResponseEntity<List<ExpenseCategory>> getCategories() {
        try {
            List<ExpenseCategory> expenseCategories = expenseCategoryService.getCategories();

            return ResponseEntity.ok(expenseCategories);
        } catch(CnemsException e) {
            return ResponseEntity.status(e.getStatus()).build();
        }
    }

    @PostMapping("/")
    ResponseEntity<SuccessMessage> addCategory(@RequestParam("name") String name, @RequestParam("description") String description) {
        try {
            expenseCategoryService.addCategory(name, description);
            return ResponseEntity.noContent().build();
        } catch (CnemsException e) {
            return ResponseEntity.status(e.getStatus()).body(new SuccessMessage(false, e.getMessage()));
        }
    }

    @PutMapping("/")
    ResponseEntity<SuccessMessage> updateCategory(@RequestParam("id") Long id, @RequestParam("name") String name, @RequestParam("description") String description) {
        try {
            expenseCategoryService.updateCategory(id, name, description);
            return ResponseEntity.ok(new SuccessMessage(true, "Updated Category Successfully"));
        } catch (CnemsException e) {
            return ResponseEntity.status(e.getStatus()).body(new SuccessMessage(false, e.getMessage()));
        }
    }

    @DeleteMapping("/")
    ResponseEntity<SuccessMessage> deleteCategory(@RequestParam("id") Long id) {
        try {
            expenseCategoryService.deleteCategory(id);
            return ResponseEntity.ok(new SuccessMessage(true, "Deleted Category Successfully"));
        } catch (CnemsException e) {
            return ResponseEntity.status(e.getStatus()).body(new SuccessMessage(false, e.getMessage()));
        }
    }
}
