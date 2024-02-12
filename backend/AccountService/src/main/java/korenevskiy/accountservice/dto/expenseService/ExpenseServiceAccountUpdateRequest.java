package korenevskiy.accountservice.dto.expenseService;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ExpenseServiceAccountUpdateRequest {
    private UUID id;
    private String name;
}
