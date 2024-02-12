package korenevskiy.expenseservice.repository;

import korenevskiy.expenseservice.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String> {
}
