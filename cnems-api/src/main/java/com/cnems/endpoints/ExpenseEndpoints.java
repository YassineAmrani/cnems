package com.cnems.endpoints;

import com.cnems.dto.SuccessMessage;
import com.cnems.dto.UserSummaryDTO;
import com.cnems.entities.Expense;
import com.cnems.exceptions.CnemsException;
import com.cnems.services.ExpenseService;
import com.cnems.services.SummaryService;
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

    @Autowired
    SummaryService summaryService;

    @GetMapping("/{id}")
    ResponseEntity<Expense> getExpense(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(expenseService.getExpense(id));
        } catch(CnemsException e) {
            return ResponseEntity.status(e.getStatus()).build();
        }
    }

    @GetMapping("/{page}")
    ResponseEntity<List<Expense>> getExpensesByDateBetween(@RequestAttribute("userId") Long userId,
                                                           @RequestParam("start_date") Date startDate,
                                                           @RequestParam("end_date") Date endDate,
                                                           @PathVariable("page") int page) {
        try{
            return ResponseEntity.ok(expenseService.getExpensesByDateBetween(userId, startDate, endDate, page));
        } catch(Exception e) {
            return ResponseEntity.status(500).build();
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

    @GetMapping("/user/{page}")
    ResponseEntity<List<Expense>> getExpensesByUserId(@RequestAttribute("userId") Long userId, @PathVariable("page") int page) {
        try {
            return ResponseEntity.ok(expenseService.getByUserId(userId, page));
        } catch(Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/account/{accountId}/{page}")
    ResponseEntity<List<Expense>> getExpensesByAccountId(@PathVariable("accountId") Long accountId, @PathVariable("page") int page) {
        try {
            return ResponseEntity.ok(expenseService.getByAccountId(accountId, page));
        } catch(Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/")
    ResponseEntity<SuccessMessage> addExpense(@RequestAttribute("userId") Long userId,
                                              @RequestParam("categoryId") Long categoryId,
                                              @RequestParam("amount") float amount,
                                              @RequestParam("date") Date date,
                                              @RequestParam("description") String description,
                                              @RequestParam("accountId") Long accountId) {
        try {
            if(userId==null || categoryId == null || date == null || description == null || accountId == null)
                return ResponseEntity.badRequest().body(new SuccessMessage(false, "Required fields are missing"));
            expenseService.addExpense(userId, categoryId, amount, date, description, accountId);
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
                                                 @RequestParam("description") String description,
                                                 @RequestParam("accountId") Long accountId) {
        try {
            if(id==null || categoryId == null || date == null || description == null)
                return ResponseEntity.badRequest().body(new SuccessMessage(false, "Required fields are missing"));
            expenseService.updateExpense(id, categoryId, amount, date, description, accountId);
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

    @GetMapping("/summary")
    ResponseEntity<UserSummaryDTO> getUserSummary(@RequestAttribute("userId") Long userId,
                                                  @RequestParam("start_date") Date startDate,
                                                  @RequestParam("end_date") Date endDate) {
        try{
            UserSummaryDTO userSummary = summaryService.getFullSummaryBetweenDates(userId,startDate,endDate);
            return ResponseEntity.ok(userSummary);
        } catch(Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
