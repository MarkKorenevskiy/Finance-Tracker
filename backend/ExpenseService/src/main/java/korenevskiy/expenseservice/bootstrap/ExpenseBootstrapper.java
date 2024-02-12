package korenevskiy.expenseservice.bootstrap;

import korenevskiy.expenseservice.model.Account;
import korenevskiy.expenseservice.model.Category;
import korenevskiy.expenseservice.model.Expense;
import korenevskiy.expenseservice.repository.AccountRepository;
import korenevskiy.expenseservice.repository.CategoryRepository;
import korenevskiy.expenseservice.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
@RequiredArgsConstructor
@Component
public class ExpenseBootstrapper implements IDevDataBootstrapper {

    private final ExpenseRepository expenseRepository;
    private final CategoryRepository categoryRepository;
    private final AccountRepository accountRepository;

    public void bootstrap() {
        log.info("Bootstrapping expense entities.");
        int EXPENSE_GENERATOR_COUNTER = 10;

        List<Expense> expenseList = new ArrayList<>();
        Random random = new Random(System.currentTimeMillis());

        // Storing all possible categories ids, then getting references to these categories
        String[] categoryIds = {
                "eating_out",
                "groceries",
                "shopping",
                "transport"
        };

        Account[] accounts = accountRepository.findAll().toArray(Account[]::new);

        List<Category> categories = new ArrayList<>();
        for (String categoryId : categoryIds) {
            Category category = categoryRepository.getReferenceById(categoryId);
            categories.add(category);
        }

        for (int i = 0; i < EXPENSE_GENERATOR_COUNTER; i++) {
            Expense expense = Expense.builder()
                    .amount(BigDecimal.valueOf(Math.random() * 100))
                    .spentAt(LocalDate.of(2023, 10, random.nextInt(1, 30)))
                    .comment("Dev expense " + i)
                    .category(categories.get(random.nextInt(0, categories.size()))) // randomly picking a category
                    .account(accounts[random.nextInt(0, accounts.length)]) // randomly picking an account
                    .build();
            expenseList.add(expense);
        }
        expenseRepository.saveAll(expenseList);
        log.info("Expense bootstrapping done.");
    }
}
