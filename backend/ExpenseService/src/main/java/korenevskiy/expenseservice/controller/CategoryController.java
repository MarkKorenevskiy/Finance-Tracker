package korenevskiy.expenseservice.controller;

import korenevskiy.expenseservice.dto.category.CategoryInfo;
import korenevskiy.expenseservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@CrossOrigin
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryInfo> getAllCategories() {
        return categoryService.getAllCategories();
    }
}
