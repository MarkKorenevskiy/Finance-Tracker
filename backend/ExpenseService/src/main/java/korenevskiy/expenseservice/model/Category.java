package korenevskiy.expenseservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Entity
@Table(name = "t_category")
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    private String id;
    private String category;

    @OneToMany(mappedBy = "category")
    private List<Expense> expenses;

}
