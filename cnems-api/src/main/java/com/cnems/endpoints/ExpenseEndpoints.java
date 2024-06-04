package com.cnems.endpoints;

import com.cnems.dto.SuccessMessage;
import com.cnems.entities.Expense;
import com.cnems.exceptions.CnemsException;
import com.cnems.services.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseEndpoints {

    @Autowired
    ExpenseService expenseService;

    @GetMapping("/{id}")
    ResponseEntity<Expense> getExpense(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(expenseService.getExpense(id));
        } catch(CnemsException e) {
            return ResponseEntity.status(e.getStatus()).build();
        }
    }

    @GetMapping("/category/{categoryId}/{page}")
    ResponseEntity<List<Expense>> getExpensesByCategory(@PathVariable("categoryId") Long categoryId, @PathVariable("page") int page) {
        try {
            return ResponseEntity.ok(expenseService.getByCategory(categoryId, page));
        } catch(CnemsException e) {
            return ResponseEntity.status(e.getStatus()).build();
        }
    }

    @PostMapping("/")
    ResponseEntity<SuccessMessage> addExpense(@RequestParam("userId") Long userId,
                                              @RequestParam("categoryId") Long categoryId,
                                              @RequestParam("amount") float amount,
                                              @RequestParam("date") Date date,
                                              @RequestParam("description") String description) {
        try {
            if(userId==null || categoryId == null || date == null || description == null)
                return ResponseEntity.badRequest().body(new SuccessMessage(false, "Required fields are missing"));
            expenseService.addExpense(userId, categoryId, amount, date, description);
            return ResponseEntity.ok(new SuccessMessage(true, "Expense added successfully"));
        } catch (CnemsException e) {
            return ResponseEntity.status(e.getStatus()).body(new SuccessMessage(false, e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<SuccessMessage> updateExpense(@PathVariable("id") Long id,
                                                 @RequestParam("categoryId") Long categoryId,
                                                 @RequestParam("amount") float amount,
                                                 @RequestParam("date") Date date,
                                                 @RequestParam("description") String description) {
        try {
            if(id==null || categoryId == null || date == null || description == null)
                return ResponseEntity.badRequest().body(new SuccessMessage(false, "Required fields are missing"));
            expenseService.updateExpense(id, categoryId, amount, date, description);
            return ResponseEntity.ok(new SuccessMessage(true, "Expense updated successfully"));
        } catch (CnemsException e) {
            return ResponseEntity.status(e.getStatus()).body(new SuccessMessage(false, e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<SuccessMessage> deleteMessage(@PathVariable("id") Long id) {
        try {
            expenseService.deleteExpense(id);
            return ResponseEntity.ok(new SuccessMessage(true, "Expense deleted successfully"));
        } catch (CnemsException e) {
            return ResponseEntity.status(e.getStatus()).body(new SuccessMessage(false, e.getMessage()));
        }
    }
}
