package korenevskiy.expenseservice.controller;

import korenevskiy.expenseservice.dto.expense.ExpenseCreateRequest;
import korenevskiy.expenseservice.dto.expense.ExpenseResponse;
import korenevskiy.expenseservice.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
@CrossOrigin
public class ExpenseController {

    private final ExpenseService expenseService;

    @GetMapping
    public List<ExpenseResponse> getAllExpenses() {
        return expenseService.getAllExpenses();
    }

    @GetMapping("/account/{accountId}")
    public List<ExpenseResponse> getExpensesForAccount(@PathVariable UUID accountId) {
        return expenseService.getExpensesForAccount(accountId);
    }

    @PostMapping("/create")
    public String createExpense(@RequestBody ExpenseCreateRequest request) {
        return expenseService.createExpense(request).toString();
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteExpense(@PathVariable UUID id) {
        return expenseService.deleteExpense(id);
    }

}
