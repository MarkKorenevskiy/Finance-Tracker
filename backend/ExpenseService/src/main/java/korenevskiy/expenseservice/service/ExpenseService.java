package korenevskiy.expenseservice.service;

import korenevskiy.expenseservice.ExpenseServiceApplication;
import korenevskiy.expenseservice.dto.account.AccountBalanceChangeRequest;
import korenevskiy.expenseservice.dto.account.AccountInfo;
import korenevskiy.expenseservice.dto.category.CategoryInfo;
import korenevskiy.expenseservice.dto.expense.ExpenseCreateRequest;
import korenevskiy.expenseservice.dto.expense.ExpenseResponse;
import korenevskiy.expenseservice.model.Account;
import korenevskiy.expenseservice.model.Category;
import korenevskiy.expenseservice.model.Expense;
import korenevskiy.expenseservice.repository.AccountRepository;
import korenevskiy.expenseservice.repository.CategoryRepository;
import korenevskiy.expenseservice.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final CategoryRepository categoryRepository;
    private final AccountRepository accountRepository;

    private final RestClient restClient;

    private final String accountServiceUri = "http://localhost:8280";

    public List<ExpenseResponse> getAllExpenses() {
        log.info("Getting all expenses");
        return expenseRepository.findAll().stream()
                .map(this::mapToExpenseResponse)
                .toList();
    }

    public List<ExpenseResponse> getExpensesForAccount(UUID accountId) {
        log.info("Getting all expenses for account with ID: {}", accountId);

        List<Expense> accountExpenses = expenseRepository.findAllByAccount_Id(accountId);
        return accountExpenses.stream()
                .map(this::mapToExpenseResponse)
                .toList();
    }

    public UUID createExpense(ExpenseCreateRequest request) {
        log.info("Creating new expense. Amount: {}, At: {}, Category: {}, Account: {}",
                request.getAmount(), request.getSpentAt(),
                request.getCategory(), request.getAccount());

        //  Mapping request to the new expense record
        Expense newExpense = mapToExpense(request);

        // Getting category reference
        Category category = categoryRepository.getReferenceById(request.getCategory());
        newExpense.setCategory(category);

        // Getting account reference
        Account account = accountRepository.getReferenceById(request.getAccount());
        newExpense.setAccount(account);

        // Sending request to AccountService to change
        // the balance of the selected account
        log.info("Sending request to AccountService to change balance accordingly");
        restClient.post()
                .uri(accountServiceUri + "/api/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .body(AccountBalanceChangeRequest.builder()
                        .accountId(request.getAccount())
                        // we get positive amount
                        // from user, so we have to make it negative
                        .balanceDelta(request.getAmount().negate())
                        .build())
                .retrieve()
                .toBodilessEntity();

        log.info("New expense created");
        return expenseRepository.save(newExpense).getId();
    }

    public boolean deleteExpense(UUID id) {

        if (id == null) {
            log.warn("ID must not be null.");
            return false;
        }

        Expense expense = expenseRepository.findById(id).orElse(null);

        if (expense == null) {
            log.warn("No expense record with provided ID: {}", id);
            return false;
        }

        UUID accountId = expense.getAccount().getId();
        BigDecimal amount = expense.getAmount();

        log.info("Deleting expense record with ID: {}", id);
        expenseRepository.deleteById(id);

        log.info("Sending request to AccountService to change balance accordingly");
        restClient.post()
                .uri(accountServiceUri + "/api/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .body(AccountBalanceChangeRequest.builder()
                        .accountId(accountId)
                        .balanceDelta(amount)
                        .build())
                .retrieve()
                .toBodilessEntity();

        log.info("Deleted.");
        return true;
    }

    private ExpenseResponse mapToExpenseResponse(Expense expense) {
        return ExpenseResponse.builder()
                .id(expense.getId())
                .amount(expense.getAmount())
                .spentAt(expense.getSpentAt())
                .comment(expense.getComment())
                .category(mapToCategoryInfo(expense.getCategory()))
                .account(mapToAccountInfo(expense.getAccount()))
                .build();
    }

    private Expense mapToExpense(ExpenseCreateRequest request) {
        return Expense.builder()
                .amount(request.getAmount())
                .spentAt(request.getSpentAt())
                .comment(request.getComment())
                .build();
    }

    private CategoryInfo mapToCategoryInfo(Category category) {
        return CategoryInfo.builder()
                .id(category.getId())
                .category(category.getCategory())
                .build();
    }

    private AccountInfo mapToAccountInfo(Account account) {
        return AccountInfo.builder()
                .id(account.getId())
                .name(account.getName())
                .build();
    }

}
