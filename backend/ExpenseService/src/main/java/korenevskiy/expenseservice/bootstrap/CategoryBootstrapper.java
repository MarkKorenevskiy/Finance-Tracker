package korenevskiy.expenseservice.bootstrap;

import korenevskiy.expenseservice.model.Category;
import korenevskiy.expenseservice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class CategoryBootstrapper implements IDevDataBootstrapper {

    private final CategoryRepository categoryRepository;

    @Override
    public void bootstrap() {
        log.info("Bootstrapping categories");

        List<Category> categories = new ArrayList<>();

        Category cat1 = Category.builder()
                .id("eating_out")
                .category("Eating out")
                .build();
        categories.add(cat1);

        Category cat2 = Category.builder()
                .id("groceries")
                .category("Groceries")
                .build();
        categories.add(cat2);

        Category cat3 = Category.builder()
                .id("shopping")
                .category("Shopping")
                .build();
        categories.add(cat3);

        Category cat4 = Category.builder()
                .id("transport")
                .category("Transport")
                .build();
        categories.add(cat4);

        categoryRepository.saveAll(categories);
    }
}
