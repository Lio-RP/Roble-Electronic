package com.roble.springproject.robleelectronic.services.imple;

import com.roble.springproject.robleelectronic.models.Category;
import com.roble.springproject.robleelectronic.repositories.CategoryRepository;
import com.roble.springproject.robleelectronic.services.CategoryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category save(Category object) {
        return categoryRepository.save(object);
    }

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        categoryRepository.findAll().forEach(categories::add);
        return categories;
    }

    @Override
    public Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId).orElse(null);
    }

    @Override
    public Category findByDescription(String description) {
        return categoryRepository.findByDescription(description);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}
