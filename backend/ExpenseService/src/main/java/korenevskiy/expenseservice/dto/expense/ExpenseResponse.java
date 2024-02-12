package korenevskiy.expenseservice.dto.expense;

import korenevskiy.expenseservice.dto.account.AccountInfo;
import korenevskiy.expenseservice.dto.category.CategoryInfo;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class ExpenseResponse {

    private UUID id;
    private BigDecimal amount;
    private LocalDate spentAt;
    private String comment;
    private CategoryInfo category;
    private AccountInfo account;
}
