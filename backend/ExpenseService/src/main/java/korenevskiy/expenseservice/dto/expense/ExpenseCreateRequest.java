package korenevskiy.expenseservice.dto.expense;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class ExpenseCreateRequest {
    private BigDecimal amount;
    private LocalDate spentAt;
    private String comment;
    private String category;
    private UUID account;
}
