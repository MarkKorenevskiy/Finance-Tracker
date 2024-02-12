package korenevskiy.accountservice.dto.account;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class AccountBalanceUpdateRequest {
    private UUID accountId;
    private BigDecimal balanceDelta;
}
