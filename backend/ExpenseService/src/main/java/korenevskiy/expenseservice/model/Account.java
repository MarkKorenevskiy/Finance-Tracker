package korenevskiy.expenseservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@Entity
@Table(name = "t_accounts")
@RequiredArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    private UUID id;
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account", fetch = FetchType.LAZY)
    private List<Expense> expenses;

}
