package korenevskiy.accountservice.dto.account;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class AccountResponseInfo {
    private UUID id;
    private String name;
    private BigDecimal balance;
}
