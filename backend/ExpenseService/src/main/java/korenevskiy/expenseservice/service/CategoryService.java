package korenevskiy.expenseservice.service;

import korenevskiy.expenseservice.dto.category.CategoryInfo;
import korenevskiy.expenseservice.model.Category;
import korenevskiy.expenseservice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<CategoryInfo> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::mapToCategoryInfo)
                .toList();
    }

    private CategoryInfo mapToCategoryInfo(Category category) {
        return CategoryInfo.builder()
                .id(category.getId())
                .category(category.getCategory())
                .build();
    }
}
