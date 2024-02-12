package korenevskiy.accountservice.dto.account;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountCreateRequest {
    private String name;
    private BigDecimal balance;
}
