package korenevskiy.expenseservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "t_expenses")
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "uuid")
    private UUID id;
    private BigDecimal amount;

    // If data from client is empty, current date will be stated to current date
    @Builder.Default
    private LocalDate spentAt = LocalDate.now();

    // Optional string value with comment about an expense
    private String comment;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne()
    @JoinColumn(name = "account_id")
    private Account account;
}
