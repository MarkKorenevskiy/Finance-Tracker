package korenevskiy.accountservice.dto.account;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class AccountUpdateInfoRequest {
    private UUID id;
    private String name;
    private BigDecimal balance;
}
