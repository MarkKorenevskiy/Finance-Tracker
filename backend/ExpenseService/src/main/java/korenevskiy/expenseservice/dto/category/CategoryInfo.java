package korenevskiy.expenseservice.dto.category;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryInfo {
    private String id;
    private String category;
}
