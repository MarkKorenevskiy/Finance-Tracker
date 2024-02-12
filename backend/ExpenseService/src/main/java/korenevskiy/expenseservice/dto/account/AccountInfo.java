package korenevskiy.expenseservice.dto.account;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class AccountInfo {
    private UUID id;
    private String name;
}
