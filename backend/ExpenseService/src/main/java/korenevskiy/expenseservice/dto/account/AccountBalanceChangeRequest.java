package korenevskiy.expenseservice.dto.account;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Data
public class AccountBalanceChangeRequest {
    private UUID accountId;
    private BigDecimal balanceDelta;
}
