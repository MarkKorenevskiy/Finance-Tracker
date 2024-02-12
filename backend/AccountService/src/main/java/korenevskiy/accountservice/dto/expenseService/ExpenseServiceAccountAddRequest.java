package korenevskiy.accountservice.dto.expenseService;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ExpenseServiceAccountAddRequest {
    private UUID id;
    private String name;
}
