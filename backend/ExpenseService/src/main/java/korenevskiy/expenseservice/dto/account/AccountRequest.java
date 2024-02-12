package korenevskiy.expenseservice.dto.account;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class AccountRequest {
    private UUID id;
    private String name;
}
